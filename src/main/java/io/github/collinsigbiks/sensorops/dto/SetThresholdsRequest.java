package io.github.collinsigbiks.sensorops.dto;

import io.github.collinsigbiks.sensorops.domain.Metric;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public record SetThresholdsRequest(@NotNull List<@NotNull @Valid ThresholdRequest> thresholds) {

    @AssertTrue(message = "each metric may appear only once")
    public boolean isMetricsUnique() {
        if (thresholds == null) {
            return true;
        }
        List<Metric> metrics = thresholds.stream()
                .filter(Objects::nonNull)
                .map(ThresholdRequest::metric)
                .filter(Objects::nonNull)
                .toList();
        Set<Metric> unique = new HashSet<>(metrics);
        return unique.size() == metrics.size();
    }
}