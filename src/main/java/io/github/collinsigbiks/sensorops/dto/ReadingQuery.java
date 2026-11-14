package io.github.collinsigbiks.sensorops.dto;

import io.github.collinsigbiks.sensorops.domain.Metric;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.Instant;
import org.springframework.format.annotation.DateTimeFormat;

public record ReadingQuery(
        Long deviceId,
        Metric metric,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
        @Min(0) Integer page,
        @Min(1) @Max(100) Integer size) {

    public ReadingQuery {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 20;
        }
    }

    @AssertTrue(message = "from must not be after to")
    public boolean isTimeRangeValid() {
        return from == null || to == null || !from.isAfter(to);
    }
}