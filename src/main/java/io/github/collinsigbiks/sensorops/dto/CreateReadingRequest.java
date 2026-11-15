package io.github.collinsigbiks.sensorops.dto;

import io.github.collinsigbiks.sensorops.domain.Metric;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record CreateReadingRequest(
        @Schema(description = "Id of the device that took the measurement", example = "2")
        @NotNull Long deviceId,

        @Schema(description = "What was measured", example = "TEMPERATURE")
        @NotNull Metric metric,

        @Schema(description = "The measured value", example = "72.5")
        @NotNull Double value,

        @Schema(description = "When the measurement was taken (ISO-8601, UTC). Optional: the server's time is used if omitted.",
                example = "2026-11-15T09:30:00Z")
        Instant recordedAt) {
}