package io.github.collinsigbiks.sensorops.controller;

import io.github.collinsigbiks.sensorops.dto.SetThresholdsRequest;
import io.github.collinsigbiks.sensorops.dto.ThresholdResponse;
import io.github.collinsigbiks.sensorops.service.ThresholdService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices/{deviceId}/thresholds")
public class ThresholdController {

    private final ThresholdService thresholdService;

    public ThresholdController(ThresholdService thresholdService) {
        this.thresholdService = thresholdService;
    }

    @PutMapping
    public List<ThresholdResponse> setThresholds(
            @PathVariable long deviceId,
            @Valid @RequestBody SetThresholdsRequest request) {
        return thresholdService.setThresholds(deviceId, request);
    }
}