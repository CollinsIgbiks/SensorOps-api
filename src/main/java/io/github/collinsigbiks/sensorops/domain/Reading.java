package io.github.collinsigbiks.sensorops.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "readings")
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    // Same column as the association above, read-only: gives the id without loading the Device
    @Column(name = "device_id", insertable = false, updatable = false)
    private Long deviceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Metric metric;

    @Column(nullable = false)
    private double value;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    protected Reading() {
        // required by JPA
    }

    public Reading(Device device, Metric metric, double value, Instant recordedAt) {
        this.device = Objects.requireNonNull(device, "device");
        this.deviceId = device.getId();
        this.metric = Objects.requireNonNull(metric, "metric");
        this.value = value;
        this.recordedAt = Objects.requireNonNull(recordedAt, "recordedAt");
    }

    public Long getId() { return id; }
    public Device getDevice() { return device; }
    public Long getDeviceId() { return deviceId; }
    public Metric getMetric() { return metric; }
    public double getValue() { return value; }
    public Instant getRecordedAt() { return recordedAt; }
}