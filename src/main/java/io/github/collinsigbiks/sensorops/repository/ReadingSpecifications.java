package io.github.collinsigbiks.sensorops.repository;

import io.github.collinsigbiks.sensorops.domain.Metric;
import io.github.collinsigbiks.sensorops.domain.Reading;
import jakarta.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public final class ReadingSpecifications {

    private ReadingSpecifications() {
        // static helpers only
    }

    public static Specification<Reading> matching(Long deviceId, Metric metric, Instant from, Instant to) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (deviceId != null) {
                predicates.add(cb.equal(root.get("deviceId"), deviceId));
            }
            if (metric != null) {
                predicates.add(cb.equal(root.get("metric"), metric));
            }
            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.<Instant>get("recordedAt"), from));
            }
            if (to != null) {
                predicates.add(cb.lessThan(root.<Instant>get("recordedAt"), to));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}