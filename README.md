# SensorOps: Telemetry API

> 🚧 **In progress: Milestone 2 of 9 done.** Part 1 of [SensorOps](docs/DESIGN.md), an end-to-end IoT monitoring platform.

A Java 25 / Spring Boot 4 REST API that stores sensor readings in PostgreSQL, checks each reading against per-device limits, and raises an alert when a limit is broken. Alerts go to an AI triage workflow ([sensorops-ai-triage](https://github.com/CollinsIgbiks/sensorops-ai-triage)), which writes its verdict back to this API.

## The problem it solves

Facilities and engineering teams get flooded with raw sensor alerts and can't tell which ones matter. This API is the reliable core: it stores every reading, flags the ones that break their limits, and keeps the full history from reading → alert → AI verdict.

## Architecture

See [the architecture diagram in the design doc](docs/DESIGN.md#2-architecture-and-data-flow). A rendered diagram will be added in Milestone 9.

## Tech

Java 25 · Spring Boot 4.1 · Spring Data JPA · PostgreSQL 18 · Flyway · springdoc-openapi (Swagger UI) · Docker

Coming in Milestone 3: JUnit 5 · Mockito · Testcontainers · JaCoCo · GitHub Actions · GHCR

## How to run it

You need [Docker Desktop](https://www.docker.com/products/docker-desktop/). Nothing else: Java and Maven run inside the build.

```bash
git clone https://github.com/CollinsIgbiks/sensorops-api.git
cd sensorops-api
cp .env.example .env        # Windows PowerShell: Copy-Item .env.example .env
# edit .env and choose a database password
docker compose up -d --build
```

Then open:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **Health check:** http://localhost:8080/actuator/health

Stop with `docker compose down` (data is kept) or `docker compose down -v` (deletes all data).

**To develop without rebuilding the image:** run only the database with `docker compose up -d db`, then the API with `./mvnw spring-boot:run`.

## API

| Method | Path | Purpose |
|---|---|---|
| POST | `/api/devices` | Register a device |
| GET | `/api/devices` | List devices, paginated |
| GET | `/api/devices/{id}` | Get one device |
| PUT | `/api/devices/{id}/thresholds` | Replace a device's min/max limits per metric |
| POST | `/api/readings` | Ingest a reading |
| GET | `/api/readings/{id}` | Get one reading |
| GET | `/api/readings?deviceId=&metric=&from=&to=` | Search readings, paginated, newest first |
| GET | `/actuator/health` | Health check |

Errors use [RFC 9457 Problem Details](https://www.rfc-editor.org/rfc/rfc9457) (`application/problem+json`), with a list of field errors for validation failures. Full details, examples and error cases are in Swagger UI.

## Design highlights

- **Layered design:** controller → service (behind interfaces) → repository → domain, with DTOs so entities are never exposed.
- **Flyway owns the schema.** Hibernate only validates it (`ddl-auto: validate`), so every change is a reviewed, versioned SQL migration.
- **Rules enforced at three levels:** request validation (clear 400s), the entity itself, and database constraints.
- **Optional search filters** built with JPA Specifications. A read-only foreign-key mapping avoids the N+1 query problem.
- **Container image:** multi-stage build (JDK to build, JRE to run), non-root user, about 173 MB compressed.
- **No secrets in the repo:** configuration comes from environment variables and a git-ignored `.env` file.

## Tests and CI

*Coming in Milestone 3.*

## Results

| Milestone | Numbers |
|---|---|
| 2: API core | 7 API endpoints + health check · 3 tables in 1 Flyway migration · Docker image ~173 MB compressed |

## What I'd do next

*To be written.*

## Licence

[MIT](LICENSE)