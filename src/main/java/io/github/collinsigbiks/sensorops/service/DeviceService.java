package io.github.collinsigbiks.sensorops.service;

import io.github.collinsigbiks.sensorops.dto.CreateDeviceRequest;
import io.github.collinsigbiks.sensorops.dto.DeviceResponse;
import io.github.collinsigbiks.sensorops.dto.PageResponse;

public interface DeviceService {

    DeviceResponse register(CreateDeviceRequest request);

    PageResponse<DeviceResponse> list(int page, int size);

    DeviceResponse get(long id);
}