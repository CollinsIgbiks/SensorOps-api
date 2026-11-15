package io.github.collinsigbiks.sensorops.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateDeviceRequest(
        @Schema(description = "Display name of the device", example = "Boiler room sensor")
        @NotBlank @Size(max = 100) String name,

        @Schema(description = "Where the device is installed", example = "Basement, plant room 2")
        @Size(max = 200) String location) {
}