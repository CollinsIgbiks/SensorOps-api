package io.github.collinsigbiks.sensorops.service;

import io.github.collinsigbiks.sensorops.dto.SetThresholdsRequest;
import io.github.collinsigbiks.sensorops.dto.ThresholdResponse;
import java.util.List;

public interface ThresholdService {

    List<ThresholdResponse> setThresholds(long deviceId, SetThresholdsRequest request);
}