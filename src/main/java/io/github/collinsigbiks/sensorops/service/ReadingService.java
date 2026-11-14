package io.github.collinsigbiks.sensorops.service;

import io.github.collinsigbiks.sensorops.dto.CreateReadingRequest;
import io.github.collinsigbiks.sensorops.dto.PageResponse;
import io.github.collinsigbiks.sensorops.dto.ReadingQuery;
import io.github.collinsigbiks.sensorops.dto.ReadingResponse;

public interface ReadingService {

    ReadingResponse ingest(CreateReadingRequest request);

    ReadingResponse get(long id);

    PageResponse<ReadingResponse> search(ReadingQuery query);
}