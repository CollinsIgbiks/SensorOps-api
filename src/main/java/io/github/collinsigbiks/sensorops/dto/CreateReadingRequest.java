package io.github.collinsigbiks.sensorops.dto;

import io.github.collinsigbiks.sensorops.domain.Metric;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record CreateReadingRequest(
        @NotNull Long deviceId,
        @NotNull Metric metric,
        @NotNull Double value,
        Instant recordedAt) {
}