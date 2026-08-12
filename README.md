# ReEntry

ReEntry is a Spring Boot learning project for rebuilding professional Spring Boot fluency through a realistic, test-driven application.

## Application Goal

ReEntry is a family reminder and calendar application. It will eventually allow family members to:

- Create, update, and view calendar events
- Create, update, and view reminders
- Generate a daily family summary
- View that summary through a lightweight React UI
- Publish the generated summary to Google Docs on demand
- Publish the generated summary through a scheduled job

Google Docs publishing is intentionally deferred until the core backend, security, frontend, and deployment foundations are stable.

## Tech Stack

- Java 21
- Spring Boot 3.5.16
- Gradle
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 for tests
- PostgreSQL for production and local development, run via Docker Compose
- Flyway for schema migrations
- springdoc-openapi (Swagger UI) for API documentation
- JUnit 5 / Spring Boot Test

## Quick Start (Local Development)

1. Make sure Docker Desktop is running.
2. Start a local Postgres instance:
   ```bash
   docker compose up -d
   ```
3. Run the app (uses the `local` profile by default, configured in `application-local.yml`):
   ```bash
   ./gradlew bootRun
   ```
4. Explore:
   - API base URL: `http://localhost:8080/api/events`
   - Swagger UI: `http://localhost:8080/swagger-ui/index.html`
   - Browse the database with a client such as [TablePlus](https://tableplus.com) or pgAdmin — connect to host `localhost`, port `5432`, database/user/password all `reentry`.

### Shutting down

```bash
# Stop the app with Ctrl+C, then:
docker compose down
```

This removes the Postgres container but preserves your data in a named Docker volume (`reentry-postgres-data`). Add `-v` to `docker compose down` only if you want to wipe that data too.

## Open in an IDE

Open the root `reentry` folder in IntelliJ IDEA, VS Code, or Eclipse as a Gradle project.

Other useful commands:

```bash
./gradlew test
```

```bash
./gradlew clean build
```

## Phase 1 Status

This scaffold includes:

- Gradle build files
- Spring Boot application class
- Package structure discipline
- Initial context-load test
- H2 test profile configuration
- Living documentation files
- Placeholder package documentation files

## Important Design Direction

Do not add Google Docs dependencies yet. ReEntry should first build out the core domain and summary-generation capability. Google Docs publishing should be introduced later as an external adapter.
