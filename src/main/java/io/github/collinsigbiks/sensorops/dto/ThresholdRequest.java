package io.github.collinsigbiks.sensorops.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.collinsigbiks.sensorops.domain.Metric;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public record ThresholdRequest(
        @Schema(description = "The metric these limits apply to", example = "TEMPERATURE")
        @NotNull Metric metric,

        @Schema(description = "Lowest acceptable value (inclusive)", example = "10")
        @NotNull Double minValue,

        @Schema(description = "Highest acceptable value (inclusive)", example = "80")
        @NotNull Double maxValue) {

    @JsonIgnore
    @AssertTrue(message = "minValue must be less than or equal to maxValue")
    public boolean isRangeValid() {
        return minValue == null || maxValue == null || minValue <= maxValue;
    }
}