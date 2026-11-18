package io.github.collinsigbiks.sensorops.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.collinsigbiks.sensorops.domain.Alert;
import io.github.collinsigbiks.sensorops.domain.AlertStatus;
import io.github.collinsigbiks.sensorops.domain.Device;
import io.github.collinsigbiks.sensorops.domain.Metric;
import io.github.collinsigbiks.sensorops.domain.Reading;
import io.github.collinsigbiks.sensorops.domain.Threshold;
import io.github.collinsigbiks.sensorops.repository.AlertRepository;
import io.github.collinsigbiks.sensorops.repository.ThresholdRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class AlertServiceImplTest {

    private static final Instant NOW = Instant.parse("2026-11-16T10:00:00Z");
    private static final long DEVICE_ID = 7L;
    private static final long READING_ID = 42L;

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private ThresholdRepository thresholdRepository;

    private AlertServiceImpl alertService;
    private Device device;

    @BeforeEach
    void setUp() {
        Clock fixedClock = Clock.fixed(NOW, ZoneOffset.UTC);
        alertService = new AlertServiceImpl(alertRepository, thresholdRepository, fixedClock);

        device = new Device("Boiler 1", "Plant room");
        ReflectionTestUtils.setField(device, "id", DEVICE_ID);   // ids are normally set by the database
    }

    @Test
    void raiseIfOutOfRange_savesOneOpenAlert_whenValueIsAboveMax() {
        givenTemperatureLimits(10.0, 30.0);
        givenSaveReturnsTheAlert();
        Reading reading = temperatureReading(35.0);

        alertService.raiseIfOutOfRange(reading);

        ArgumentCaptor<Alert> saved = ArgumentCaptor.forClass(Alert.class);
        verify(alertRepository).save(saved.capture());   // exactly once
        Alert alert = saved.getValue();
        assertThat(alert.getStatus()).isEqualTo(AlertStatus.OPEN);
        assertThat(alert.getReadingId()).isEqualTo(READING_ID);
        assertThat(alert.getDeviceId()).isEqualTo(DEVICE_ID);
        assertThat(alert.getCreatedAt()).isEqualTo(NOW);
        assertThat(alert.getUpdatedAt()).isEqualTo(NOW);
        assertThat(alert.getAiUrgency()).isNull();       // no AI verdict yet
    }

    @Test
    void raiseIfOutOfRange_savesAlert_whenValueIsBelowMin() {
        givenTemperatureLimits(10.0, 30.0);
        givenSaveReturnsTheAlert();

        alertService.raiseIfOutOfRange(temperatureReading(5.0));

        verify(alertRepository).save(any(Alert.class));
    }

    @Test
    void raiseIfOutOfRange_savesNothing_whenValueIsInRange() {
        givenTemperatureLimits(10.0, 30.0);

        alertService.raiseIfOutOfRange(temperatureReading(30.0));   // exactly at max

        verify(alertRepository, never()).save(any());
    }

    @Test
    void raiseIfOutOfRange_savesNothing_whenNoThresholdIsSet() {
        when(thresholdRepository.findByDeviceIdAndMetric(DEVICE_ID, Metric.TEMPERATURE))
                .thenReturn(Optional.empty());

        alertService.raiseIfOutOfRange(temperatureReading(999.0));

        verify(alertRepository, never()).save(any());
    }

    private void givenTemperatureLimits(double min, double max) {
        when(thresholdRepository.findByDeviceIdAndMetric(DEVICE_ID, Metric.TEMPERATURE))
                .thenReturn(Optional.of(new Threshold(device, Metric.TEMPERATURE, min, max)));
    }

    // Only stubbed where a save is expected: Mockito's strict stubs fail a test on unused stubs
    private void givenSaveReturnsTheAlert() {
        when(alertRepository.save(any(Alert.class))).thenAnswer(call -> call.getArgument(0));
    }

    private Reading temperatureReading(double value) {
        Reading reading = new Reading(device, Metric.TEMPERATURE, value, NOW);
        ReflectionTestUtils.setField(reading, "id", READING_ID);
        return reading;
    }
}
