# SensorOps: Telemetry API

> ðŸš§ **In progress.** Part 1 of [SensorOps](docs/DESIGN.md), an end-to-end IoT monitoring platform.

A Java 25 / Spring Boot 4 REST API that stores sensor readings in PostgreSQL, checks each reading against per-device limits, and raises an alert when a limit is broken. Alerts go to an AI triage workflow ([sensorops-ai-triage](https://github.com/CollinsIgbiks/sensorops-ai-triage)), which writes its verdict back to this API.

## The problem it solves
Facilities and engineering teams get flooded with raw sensor alerts and can't tell which ones matter. This API is the reliable core: it stores every reading, flags the ones that break their limits, and keeps the full history from reading â†’ alert â†’ AI verdict.

## Architecture
See [the architecture diagram in the design doc](docs/DESIGN.md#2-architecture-and-data-flow). A rendered diagram will be added in Milestone 9.

## Tech
Java 25 Â· Spring Boot 4 Â· Spring Data JPA Â· PostgreSQL Â· Flyway Â· springdoc-openapi (Swagger UI) Â· JUnit 5 Â· Mockito Â· Testcontainers Â· JaCoCo Â· Docker Â· GitHub Actions Â· GHCR

## How to run it
_Coming in Milestone 2:_ `docker compose up`

## Tests and CI
_Coming in Milestone 3._

## Results
_Real numbers will be added as each milestone is completed._

## What I'd do next
_To be written._

## Licence
[MIT](LICENSE)
