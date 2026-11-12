package io.github.collinsigbiks.sensorops.service;

import io.github.collinsigbiks.sensorops.dto.CreateReadingRequest;
import io.github.collinsigbiks.sensorops.dto.ReadingResponse;

public interface ReadingService {

    ReadingResponse ingest(CreateReadingRequest request);

    ReadingResponse get(long id);
}