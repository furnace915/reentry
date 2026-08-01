package com.example.reentry.service;

import com.example.reentry.dto.CreateEventRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EventServiceImplTest {

    private final EventServiceImpl eventService = new EventServiceImpl();

    @Test
    void shouldRejectEventWhenEndTimeIsBeforeStartTime() {
        LocalDateTime startTime = LocalDateTime.of(2026, 8, 1, 11, 0);
        LocalDateTime endTime = LocalDateTime.of(2026, 8, 1, 10, 0);
        CreateEventRequest request = new CreateEventRequest(
                "Dentist appointment",
                "Annual checkup",
                startTime,
                endTime);

        assertThrows(IllegalArgumentException.class, () -> eventService.createEvent(request));
    }
}
