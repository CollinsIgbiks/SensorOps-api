package io.github.collinsigbiks.sensorops.service;

import io.github.collinsigbiks.sensorops.dto.CreateDeviceRequest;
import io.github.collinsigbiks.sensorops.dto.DeviceResponse;

public interface DeviceService {

    DeviceResponse register(CreateDeviceRequest request);
}