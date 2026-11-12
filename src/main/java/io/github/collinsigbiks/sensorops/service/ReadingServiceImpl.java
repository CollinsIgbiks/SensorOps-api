package io.github.collinsigbiks.sensorops.service;

import io.github.collinsigbiks.sensorops.domain.Device;
import io.github.collinsigbiks.sensorops.domain.Reading;
import io.github.collinsigbiks.sensorops.dto.CreateReadingRequest;
import io.github.collinsigbiks.sensorops.dto.ReadingResponse;
import io.github.collinsigbiks.sensorops.exception.ResourceNotFoundException;
import io.github.collinsigbiks.sensorops.repository.DeviceRepository;
import io.github.collinsigbiks.sensorops.repository.ReadingRepository;
import java.time.Clock;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReadingServiceImpl implements ReadingService {

    private final DeviceRepository deviceRepository;
    private final ReadingRepository readingRepository;
    private final Clock clock;

    public ReadingServiceImpl(DeviceRepository deviceRepository,
                              ReadingRepository readingRepository,
                              Clock clock) {
        this.deviceRepository = deviceRepository;
        this.readingRepository = readingRepository;
        this.clock = clock;
    }

    @Override
    public ReadingResponse ingest(CreateReadingRequest request) {
        Device device = deviceRepository.findById(request.deviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Device", request.deviceId()));
        Instant recordedAt = request.recordedAt() != null ? request.recordedAt() : clock.instant();

        Reading reading = readingRepository.save(
                new Reading(device, request.metric(), request.value(), recordedAt));

        // Milestone 3: check the reading against its threshold and raise an alert here.
        return ReadingResponse.from(reading);
    }

    @Override
    @Transactional(readOnly = true)
    public ReadingResponse get(long id) {
        return readingRepository.findById(id)
                .map(ReadingResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Reading", id));
    }
}