package io.github.collinsigbiks.sensorops.dto;

import io.github.collinsigbiks.sensorops.domain.Metric;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public record ThresholdRequest(
        @NotNull Metric metric,
        @NotNull Double minValue,
        @NotNull Double maxValue) {

    @AssertTrue(message = "minValue must be less than or equal to maxValue")
    public boolean isRangeValid() {
        return minValue == null || maxValue == null || minValue <= maxValue;
    }
}