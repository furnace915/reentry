package com.example.reentry.service;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;

public interface EventService {

    EventResponse createEvent(CreateEventRequest request);
}
