package com.example.reentry.service;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface EventService {

    EventResponse createEvent(CreateEventRequest request);

    EventResponse getEventById(UUID id);

    EventResponse updateEvent(UUID id, @Valid CreateEventRequest request);

    List<EventResponse> getEventsForFamilyMember(UUID familyMemberId);
}
