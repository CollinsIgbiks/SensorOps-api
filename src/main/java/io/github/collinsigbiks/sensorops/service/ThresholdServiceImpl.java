package io.github.collinsigbiks.sensorops.service;

import io.github.collinsigbiks.sensorops.domain.Device;
import io.github.collinsigbiks.sensorops.domain.Metric;
import io.github.collinsigbiks.sensorops.domain.Threshold;
import io.github.collinsigbiks.sensorops.dto.SetThresholdsRequest;
import io.github.collinsigbiks.sensorops.dto.ThresholdRequest;
import io.github.collinsigbiks.sensorops.dto.ThresholdResponse;
import io.github.collinsigbiks.sensorops.exception.ResourceNotFoundException;
import io.github.collinsigbiks.sensorops.repository.DeviceRepository;
import io.github.collinsigbiks.sensorops.repository.ThresholdRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ThresholdServiceImpl implements ThresholdService {

    private final DeviceRepository deviceRepository;
    private final ThresholdRepository thresholdRepository;

    public ThresholdServiceImpl(DeviceRepository deviceRepository,
                                ThresholdRepository thresholdRepository) {
        this.deviceRepository = deviceRepository;
        this.thresholdRepository = thresholdRepository;
    }

    @Override
    public List<ThresholdResponse> setThresholds(long deviceId, SetThresholdsRequest request) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device", deviceId));

        Map<Metric, Threshold> existing = thresholdRepository.findByDeviceId(deviceId).stream()
                .collect(Collectors.toMap(Threshold::getMetric, Function.identity()));

        List<Threshold> result = new ArrayList<>();
        for (ThresholdRequest wanted : request.thresholds()) {
            Threshold threshold = existing.remove(wanted.metric());
            if (threshold == null) {
                threshold = thresholdRepository.save(new Threshold(
                        device, wanted.metric(), wanted.minValue(), wanted.maxValue()));
            } else {
                threshold.updateLimits(wanted.minValue(), wanted.maxValue());
            }
            result.add(threshold);
        }
        thresholdRepository.deleteAll(existing.values());

        return result.stream()
                .sorted(Comparator.comparing(Threshold::getMetric))
                .map(ThresholdResponse::from)
                .toList();
    }
}