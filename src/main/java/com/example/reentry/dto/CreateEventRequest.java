package com.example.reentry.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CreateEventRequest(
        @NotBlank String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime) {
}
