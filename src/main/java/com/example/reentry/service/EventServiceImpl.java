package com.example.reentry.service;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    @Override
    public EventResponse createEvent(CreateEventRequest request) {
        if (request.endTime().isBefore(request.startTime())) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
        return new EventResponse(
                UUID.randomUUID(),
                request.title(),
                request.description(),
                request.startTime(),
                request.endTime());
    }
}
