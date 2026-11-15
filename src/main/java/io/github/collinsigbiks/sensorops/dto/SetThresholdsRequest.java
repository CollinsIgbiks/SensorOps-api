package io.github.collinsigbiks.sensorops.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.collinsigbiks.sensorops.domain.Metric;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public record SetThresholdsRequest(
        @Schema(description = "The complete set of thresholds for the device. Metrics left out are removed.")
        @NotNull List<@NotNull @Valid ThresholdRequest> thresholds) {

    @JsonIgnore
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