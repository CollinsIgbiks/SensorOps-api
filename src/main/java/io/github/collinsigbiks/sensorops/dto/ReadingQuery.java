package io.github.collinsigbiks.sensorops.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.collinsigbiks.sensorops.domain.Metric;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.Instant;
import org.springframework.format.annotation.DateTimeFormat;

public record ReadingQuery(
        @Parameter(description = "Only readings from this device")
        Long deviceId,

        @Parameter(description = "Only readings of this metric")
        Metric metric,

        @Parameter(description = "Earliest time, included (ISO-8601, e.g. 2026-11-15T00:00:00Z)")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,

        @Parameter(description = "Latest time, excluded (ISO-8601, e.g. 2026-11-16T00:00:00Z)")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,

        @Parameter(description = "Page number, starting at 0")
        @Min(0) Integer page,

        @Parameter(description = "Items per page, 1 to 100")
        @Min(1) @Max(100) Integer size) {

    public ReadingQuery {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 20;
        }
    }

    @JsonIgnore
    @AssertTrue(message = "from must not be after to")
    public boolean isTimeRangeValid() {
        return from == null || to == null || !from.isAfter(to);
    }
}