package io.github.collinsigbiks.sensorops.controller;

import io.github.collinsigbiks.sensorops.dto.CreateDeviceRequest;
import io.github.collinsigbiks.sensorops.dto.DeviceResponse;
import io.github.collinsigbiks.sensorops.dto.PageResponse;
import io.github.collinsigbiks.sensorops.service.DeviceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> register(@Valid @RequestBody CreateDeviceRequest request) {
        DeviceResponse created = deviceService.register(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public PageResponse<DeviceResponse> list(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return deviceService.list(page, size);
    }

    @GetMapping("/{id}")
    public DeviceResponse get(@PathVariable long id) {
        return deviceService.get(id);
    }
}