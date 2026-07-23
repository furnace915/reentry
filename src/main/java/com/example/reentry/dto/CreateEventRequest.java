package com.example.reentry.dto;

import java.time.LocalDateTime;

public record CreateEventRequest(String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
}
