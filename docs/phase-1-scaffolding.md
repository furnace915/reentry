# Phase 1 — Project Scaffolding & Spring Fundamentals

## Brief Orientation

This phase creates the initial ReEntry Spring Boot Gradle project. The goal is to establish a clean foundation that opens in an IDE, has the correct package structure, includes the required dependencies, and verifies that the Spring application context can load.

## What This Phase Includes

- Gradle build using `build.gradle`
- Java 21 configuration
- Spring Boot 3.5.16 plugin
- Spring Web
- Spring Data JPA
- Validation
- H2
- MySQL driver
- DevTools
- Spring Boot Test
- Initial application class
- Initial `@SpringBootTest`
- Living documentation files

## Artifact Expectations

Section 1 should produce a usable project artifact that can be opened directly in an IDE, built, and tested successfully. The artifact should include:

- A working Gradle wrapper-based project root
- A build that passes with `./gradlew test`
- A notes file in the artifact that documents every file added or changed for this phase, including a short summary of each file's contents and purpose

## Master Prompt Alignment

This phase is intentionally scoped to the first milestone of the master prompt: establishing a runnable Spring Boot foundation for the ReEntry family reminder and calendar application. It should:

- create a project that opens cleanly in an IDE and builds/tests successfully
- preserve the future application direction of family reminders, calendar events, summaries, and later integrations
- avoid introducing Google Docs publishing dependencies or other later-stage features before the core backend foundation is in place

## Execution

Run:

```bash
./gradlew test
```

The context-load test should pass once dependencies are resolved.

## Annotation Literacy

### `@SpringBootApplication`

Combines configuration, auto-configuration, and component scanning. Because it is located in `com.example.reentry`, Spring scans that package and all child packages.

### `@SpringBootTest`

Loads the full Spring application context. Use it for meaningful acceptance/integration confidence, not for every small unit test.

### `@Test`

Marks a method as a JUnit test method.

## Architectural Rationale

The project starts with package structure and documentation before feature code so future sessions stay consistent. Google Docs is documented as a future integration but not implemented yet.

## Common Mistakes

- Adding Google Docs dependencies too early
- Returning JPA entities directly from future controllers
- Mixing Maven and Gradle instructions
- Putting code into vague `utils` packages
- Using `@SpringBootTest` when a slice or unit test is enough
