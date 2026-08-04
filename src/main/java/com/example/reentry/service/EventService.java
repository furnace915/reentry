package com.example.reentry.service;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;

import java.util.UUID;

public interface EventService {

    EventResponse createEvent(CreateEventRequest request);

    EventResponse getEventById(UUID id);
}
