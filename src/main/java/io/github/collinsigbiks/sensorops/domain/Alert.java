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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reading_id", nullable = false, updatable = false)
    private Reading reading;

    // Read-only copies of the foreign keys: give the ids without loading the associations
    @Column(name = "reading_id", insertable = false, updatable = false)
    private Long readingId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false, updatable = false)
    private Device device;

    @Column(name = "device_id", insertable = false, updatable = false)
    private Long deviceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AlertStatus status;

    @Column(name = "ai_category", length = 50)
    private String aiCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "ai_urgency", length = 20)
    private AlertUrgency aiUrgency;

    @Column(name = "ai_explanation", length = 1000)
    private String aiExplanation;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Alert() {
        // required by JPA
    }

    private Alert(Reading reading, Instant now) {
        this.reading = Objects.requireNonNull(reading, "reading");
        this.readingId = reading.getId();
        this.device = reading.getDevice();
        this.deviceId = reading.getDeviceId();
        this.status = AlertStatus.OPEN;
        this.createdAt = Objects.requireNonNull(now, "now");
        this.updatedAt = now;
    }

    /** A new alert always starts OPEN, with no AI verdict yet. */
    public static Alert open(Reading reading, Instant now) {
        return new Alert(reading, now);
    }

    public Long getId() { return id; }
    public Reading getReading() { return reading; }
    public Long getReadingId() { return readingId; }
    public Device getDevice() { return device; }
    public Long getDeviceId() { return deviceId; }
    public AlertStatus getStatus() { return status; }
    public String getAiCategory() { return aiCategory; }
    public AlertUrgency getAiUrgency() { return aiUrgency; }
    public String getAiExplanation() { return aiExplanation; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
