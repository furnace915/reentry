# MySQL Compatibility Notes

## Phase 1 Decision

Tests use H2 in MySQL compatibility mode:

```text
jdbc:h2:mem:reentry_test;MODE=MySQL
```

## Rationale

H2 provides fast feedback for test runs, but it is not a perfect MySQL substitute.

## Future Follow-Up

Before production deployment, validate schema behavior against a real MySQL 8.x instance.
