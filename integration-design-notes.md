# Integration Design Notes

## Future Integration Target

Google Docs publishing.

## Phase 1 Decision

Do not add Google API dependencies yet.

## Rationale

Summary generation should be implemented as a core application capability first. Google Docs publishing should later be introduced as an external adapter that can publish an already-generated summary either on demand or through a scheduled job.

## Direction

The future flow should be:

```text
Controller or Scheduler
    -> Application Service
    -> Summary Generation
    -> Publisher Interface
    -> Google Docs Adapter
```
