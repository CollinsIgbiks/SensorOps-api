package io.github.collinsigbiks.sensorops.service;

import io.github.collinsigbiks.sensorops.domain.Reading;

public interface AlertService {

    /**
     * Raises one OPEN alert if the reading is outside its device's threshold for that metric.
     * Does nothing if the reading is in range or no threshold is set.
     * Must run inside the transaction that saved the reading.
     */
    void raiseIfOutOfRange(Reading reading);
}
