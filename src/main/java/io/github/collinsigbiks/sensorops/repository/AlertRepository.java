package io.github.collinsigbiks.sensorops.repository;

import io.github.collinsigbiks.sensorops.domain.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}
