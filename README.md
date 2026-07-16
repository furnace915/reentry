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
- MySQL driver for production
- JUnit 5 / Spring Boot Test

## Open in an IDE

Open the root `reentry` folder in IntelliJ IDEA, VS Code, or Eclipse as a Gradle project.

Useful commands:

```bash
./gradlew test
```

```bash
./gradlew clean build
```

```bash
./gradlew bootRun
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
