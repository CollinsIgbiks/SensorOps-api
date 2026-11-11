package io.github.collinsigbiks.sensorops.service;

import io.github.collinsigbiks.sensorops.domain.Device;
import io.github.collinsigbiks.sensorops.dto.CreateDeviceRequest;
import io.github.collinsigbiks.sensorops.dto.DeviceResponse;
import io.github.collinsigbiks.sensorops.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public DeviceResponse register(CreateDeviceRequest request) {
        Device device = new Device(request.name().trim(), request.location());
        return DeviceResponse.from(deviceRepository.save(device));
    }
}