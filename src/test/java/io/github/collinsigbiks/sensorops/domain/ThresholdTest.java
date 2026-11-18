package io.github.collinsigbiks.sensorops.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ThresholdTest {

    // Limits 10.0 .. 30.0 for every case
    private final Threshold threshold =
            new Threshold(new Device("Boiler 1", "Plant room"), Metric.TEMPERATURE, 10.0, 30.0);

    @ParameterizedTest(name = "value {0} -> breached = {1}")
    @CsvSource({
            "9.99,  true",   // just below min
            "10.0,  false",  // exactly at min: limits are inclusive
            "20.0,  false",  // inside
            "30.0,  false",  // exactly at max: limits are inclusive
            "30.01, true",   // just above max
            "-5.0,  true"    // far below (negative values are valid readings)
    })
    void isBreachedBy_treatsLimitsAsInclusive(double value, boolean expected) {
        assertThat(threshold.isBreachedBy(value)).isEqualTo(expected);
    }
}
