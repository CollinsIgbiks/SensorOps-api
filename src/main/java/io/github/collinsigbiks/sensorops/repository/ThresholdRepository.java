package io.github.collinsigbiks.sensorops.repository;

import io.github.collinsigbiks.sensorops.domain.Metric;
import io.github.collinsigbiks.sensorops.domain.Threshold;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThresholdRepository extends JpaRepository<Threshold, Long> {

    List<Threshold> findByDeviceId(Long deviceId);

    // At most one match: the table has UNIQUE (device_id, metric)
    Optional<Threshold> findByDeviceIdAndMetric(Long deviceId, Metric metric);
}
