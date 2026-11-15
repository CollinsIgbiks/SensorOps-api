package io.github.collinsigbiks.sensorops.controller;

import io.github.collinsigbiks.sensorops.dto.SetThresholdsRequest;
import io.github.collinsigbiks.sensorops.dto.ThresholdResponse;
import io.github.collinsigbiks.sensorops.service.ThresholdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Thresholds", description = "Set the min/max limits that decide when a reading is out of range")
@RestController
@RequestMapping("/api/devices/{deviceId}/thresholds")
public class ThresholdController {

    private final ThresholdService thresholdService;

    public ThresholdController(ThresholdService thresholdService) {
        this.thresholdService = thresholdService;
    }

    @Operation(summary = "Replace a device's thresholds",
            description = """
                    Sets the limits per metric. The request replaces the device's whole set: \
                    metrics left out are removed. Sending the same request twice gives the same result.""")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The device's thresholds after the update"),
            @ApiResponse(responseCode = "400", description = "Invalid limits, e.g. min above max or a metric listed twice",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "No device with this id",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping
    public List<ThresholdResponse> setThresholds(
            @Parameter(description = "Device id") @PathVariable long deviceId,
            @Valid @RequestBody SetThresholdsRequest request) {
        return thresholdService.setThresholds(deviceId, request);
    }
}