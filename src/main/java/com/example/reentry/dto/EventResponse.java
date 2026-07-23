package com.example.reentry.dto;

import java.time.LocalDateTime;

public record EventResponse(
        String id,
        String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime
) {}
