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

@Entity
@Table(name = "thresholds")
public class Threshold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Metric metric;

    @Column(name = "min_value", nullable = false)
    private double minValue;

    @Column(name = "max_value", nullable = false)
    private double maxValue;

    protected Threshold() {
        // required by JPA
    }

    public Threshold(Device device, Metric metric, double minValue, double maxValue) {
        requireValidRange(minValue, maxValue);
        this.device = device;
        this.metric = metric;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void updateLimits(double minValue, double maxValue) {
        requireValidRange(minValue, maxValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private static void requireValidRange(double minValue, double maxValue) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException("minValue must not exceed maxValue");
        }
    }

    public Long getId() { return id; }
    public Metric getMetric() { return metric; }
    public double getMinValue() { return minValue; }
    public double getMaxValue() { return maxValue; }
}