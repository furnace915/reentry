# Master Prompt Reconciliation

This document captures the working interpretation of the master prompt for the ReEntry project so future sessions stay aligned with the intended architecture and learning goals.

## Resolved Application Description

ReEntry is a Spring Boot learning project for a family reminder and calendar application. It should eventually support:

- creating, updating, and viewing calendar events
- creating, updating, and viewing reminders
- generating a daily family summary
- exposing that summary through a lightweight UI
- publishing the summary to Google Docs later as an external integration

## Project Intent for Future Sessions

The project should be built in phases, with each session staying focused on the current milestone and avoiding premature integration work.

The current scaffold is the Phase 1 foundation for that broader plan.

## Non-Negotiables From the Master Prompt

- Use Gradle only; do not introduce Maven files or Maven commands.
- Use the Gradle wrapper committed to the repository.
- Use Java 21.
- Use Spring Boot 3.x; the current scaffold uses 3.5.16.
- Prefer constructor injection only.
- Follow outside-in TDD by default.
- Prefer slice tests over full context loads when appropriate.
- Use H2 for testing and MySQL for production-oriented thinking.
- Keep the package structure consistent with the production-style layout rooted at com.example.reentry.
- Never return JPA entities directly from controllers.
- Keep API contracts separated from persistence models through DTOs where appropriate.
- Delay Google Docs integration until the core domain and application services are stable.

## Required Package Structure

The project should continue to use this layout:

- config
- controller
- dto
- exception
- integration
- model
- repository
- scheduler
- service
- validation

Avoid vague packages such as util, common, helpers, or misc unless there is a strong architectural reason.

## Build and Test Expectations

Use wrapper-based commands such as:

```bash
./gradlew test
./gradlew clean build
./gradlew bootRun
```

The current repository was verified successfully with:

```bash
./gradlew test
```

That command completed successfully with exit code 0.

## Guardrails for Future Sessions

### Do not introduce these too early

- Google Docs dependencies
- Google API clients
- scheduling/publishing logic before the domain is ready
- controller-level business logic
- field injection
- direct entity exposure from the web layer

### Do prioritize these early

- a runnable Spring Boot application that opens in an IDE
- working Gradle test execution
- clear package boundaries
- service-layer business logic
- DTO-based API contracts
- test-driven development habits
- clean seams for future integrations

## Current Repository Status

The repository currently has a working Phase 1 scaffold that includes:

- a Gradle-based Spring Boot application
- a runnable wrapper-based build
- a basic Spring Boot test
- a package structure aligned with the master prompt
- documentation files for the project plan and artifact notes

## Suggested Next Session Prompt

Use a prompt like this for the next session:

```text
Generate Phase 2: introduce dependency injection and the bean model in a Spring Boot 3.x Gradle project, using constructor injection, a small service class, and a unit test that verifies the service is wired correctly without introducing any new external integrations.
```
