package com.example.reentry.service;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import com.example.reentry.exception.EventNotFoundException;
import com.example.reentry.mapper.EventMapper;
import com.example.reentry.model.Event;
import com.example.reentry.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public EventResponse createEvent(CreateEventRequest request) {
        if (request.endTime().isBefore(request.startTime())) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }

        Event eventToSave = eventMapper.toEntity(request);
        Event savedEvent = eventRepository.save(eventToSave);

        return eventMapper.toResponse(savedEvent);
    }

    @Override
    public EventResponse getEventById(UUID eventId) {
        return eventRepository.findById(eventId)
                .map(eventMapper::toResponse)
                .orElseThrow(() -> new EventNotFoundException("Event with id " + eventId + " not found"));
    }

    @Override
    public EventResponse updateEvent(UUID id, CreateEventRequest request) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event with id " + id + " not found"));

        Event savedEvent = eventRepository.save(eventMapper.toEntity(request, existingEvent));

        return eventMapper.toResponse(savedEvent);
    }

    @Override
    public List<EventResponse> getEventsForFamilyMember(UUID familyMemberId) {
        return Collections.emptyList(); // Placeholder for actual implementation
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(eventMapper::toResponse)
                .toList();
    }
}
