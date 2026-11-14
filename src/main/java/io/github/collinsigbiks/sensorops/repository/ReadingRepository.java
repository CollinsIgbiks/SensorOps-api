package io.github.collinsigbiks.sensorops.repository;

import io.github.collinsigbiks.sensorops.domain.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingRepository
        extends JpaRepository<Reading, Long>, JpaSpecificationExecutor<Reading> {
}