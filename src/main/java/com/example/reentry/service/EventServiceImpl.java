package com.example.reentry.service;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    @Override
    public EventResponse createEvent(CreateEventRequest request) {
        return new EventResponse(
                UUID.randomUUID().toString(),
                request.title(),
                request.description(),
                request.startTime(),
                request.endTime());
    }
}
