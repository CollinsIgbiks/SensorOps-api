package io.github.collinsigbiks.sensorops.controller;

import io.github.collinsigbiks.sensorops.dto.CreateReadingRequest;
import io.github.collinsigbiks.sensorops.dto.PageResponse;
import io.github.collinsigbiks.sensorops.dto.ReadingQuery;
import io.github.collinsigbiks.sensorops.dto.ReadingResponse;
import io.github.collinsigbiks.sensorops.service.ReadingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "Readings", description = "Send sensor readings and query their history")
@RestController
@RequestMapping("/api/readings")
public class ReadingController {

    private final ReadingService readingService;

    public ReadingController(ReadingService readingService) {
        this.readingService = readingService;
    }

    @Operation(summary = "Ingest a reading",
            description = "Stores one measurement from a device. If recordedAt is omitted, the server's time is used.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reading stored"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "No device with this deviceId",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ReadingResponse> ingest(@Valid @RequestBody CreateReadingRequest request) {
        ReadingResponse created = readingService.ingest(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Get a reading")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The reading"),
            @ApiResponse(responseCode = "404", description = "No reading with this id",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ReadingResponse get(@Parameter(description = "Reading id") @PathVariable long id) {
        return readingService.get(id);
    }

    @Operation(summary = "Search readings",
            description = """
                    All filters are optional and combine. Results are newest first. \
                    The time range includes `from` and excludes `to`. No matches is a 200 with an empty page.""")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A page of readings"),
            @ApiResponse(responseCode = "400", description = "Invalid filter, time range or paging",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public PageResponse<ReadingResponse> search(@Valid @ParameterObject ReadingQuery query) {
        return readingService.search(query);
    }
}