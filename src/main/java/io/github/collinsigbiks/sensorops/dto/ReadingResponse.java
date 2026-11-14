package io.github.collinsigbiks.sensorops.dto;

import io.github.collinsigbiks.sensorops.domain.Metric;
import io.github.collinsigbiks.sensorops.domain.Reading;
import java.time.Instant;

public record ReadingResponse(Long id, Long deviceId, Metric metric, double value, Instant recordedAt) {

    public static ReadingResponse from(Reading reading) {
        return new ReadingResponse(
                reading.getId(), reading.getDeviceId(), reading.getMetric(),
                reading.getValue(), reading.getRecordedAt());
    }
}