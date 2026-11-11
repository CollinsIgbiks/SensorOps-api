package io.github.collinsigbiks.sensorops.repository;

import io.github.collinsigbiks.sensorops.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}