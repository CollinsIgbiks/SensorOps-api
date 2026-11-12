package io.github.collinsigbiks.sensorops.dto;

import io.github.collinsigbiks.sensorops.domain.Metric;
import io.github.collinsigbiks.sensorops.domain.Threshold;

public record ThresholdResponse(Metric metric, double minValue, double maxValue) {

    public static ThresholdResponse from(Threshold threshold) {
        return new ThresholdResponse(
                threshold.getMetric(), threshold.getMinValue(), threshold.getMaxValue());
    }
}