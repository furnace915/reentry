package com.example.reentry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Request body for creating or fully replacing a calendar event")
public record CreateEventRequest(

        @Schema(description = "Event name", example = "Dentist appointment", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String name,

        @Schema(description = "Optional event description", example = "Annual checkup")
        String description,

        @Schema(description = "Start date and time", example = "2026-08-11T10:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull LocalDateTime startTime,

        @Schema(description = "End date and time; must not be before startTime", example = "2026-08-11T11:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull LocalDateTime endTime,

        @Schema(description = "True if this event applies to the whole family, rather than specific individuals", example = "false")
        boolean familyEvent) {
}
