package io.github.collinsigbiks.sensorops.service;

import io.github.collinsigbiks.sensorops.domain.Alert;
import io.github.collinsigbiks.sensorops.domain.Reading;
import io.github.collinsigbiks.sensorops.repository.AlertRepository;
import io.github.collinsigbiks.sensorops.repository.ThresholdRepository;
import java.time.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlertServiceImpl implements AlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertServiceImpl.class);

    private final AlertRepository alertRepository;
    private final ThresholdRepository thresholdRepository;
    private final Clock clock;

    public AlertServiceImpl(AlertRepository alertRepository,
                            ThresholdRepository thresholdRepository,
                            Clock clock) {
        this.alertRepository = alertRepository;
        this.thresholdRepository = thresholdRepository;
        this.clock = clock;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void raiseIfOutOfRange(Reading reading) {
        thresholdRepository.findByDeviceIdAndMetric(reading.getDeviceId(), reading.getMetric())
                .filter(threshold -> threshold.isBreachedBy(reading.getValue()))
                .ifPresent(threshold -> {
                    Alert alert = alertRepository.save(Alert.open(reading, clock.instant()));
                    log.info("Alert {} raised: device {} {} = {} outside [{}, {}]",
                            alert.getId(), reading.getDeviceId(), reading.getMetric(),
                            reading.getValue(), threshold.getMinValue(), threshold.getMaxValue());
                });
    }
}
