package com.example.reentry.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "A calendar event")
public record EventResponse(

        @Schema(description = "Event id, generated on creation")
        UUID id,

        @Schema(description = "Event name", example = "Dentist appointment")
        String name,

        @Schema(description = "Event description", example = "Annual checkup")
        String description,

        @Schema(description = "Start date and time", example = "2026-08-11T10:00:00")
        LocalDateTime startTime,

        @Schema(description = "End date and time", example = "2026-08-11T11:00:00")
        LocalDateTime endTime,

        @Schema(description = "True if this event applies to the whole family, rather than specific individuals", example = "false")
        boolean familyEvent
) {}
