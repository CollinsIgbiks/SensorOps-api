package io.github.collinsigbiks.sensorops.dto;

import io.github.collinsigbiks.sensorops.domain.Device;
import java.time.Instant;

public record DeviceResponse(Long id, String name, String location, Instant createdAt) {

    public static DeviceResponse from(Device device) {
        return new DeviceResponse(
                device.getId(), device.getName(), device.getLocation(), device.getCreatedAt());
    }
}