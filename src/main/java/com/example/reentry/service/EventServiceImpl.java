package com.example.reentry.service;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import com.example.reentry.exception.EventNotFoundException;
import com.example.reentry.model.Event;
import com.example.reentry.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventResponse createEvent(CreateEventRequest request) {
        if (request.endTime().isBefore(request.startTime())) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }

        Event eventToSave = new Event(request.name(), request.description(), request.startTime(), request.endTime());
        Event savedEvent = eventRepository.save(eventToSave);

        return new EventResponse(
                savedEvent.getId(),
                savedEvent.getName(),
                savedEvent.getDescription(),
                savedEvent.getStartTime(),
                savedEvent.getEndTime());
    }

    @Override
    public EventResponse getEventById(UUID eventId) {
        return eventRepository.findById(eventId)
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getName(),
                        event.getDescription(),
                        event.getStartTime(),
                        event.getEndTime()))
                .orElseThrow(() -> new EventNotFoundException("Event with id " + eventId + " not found"));
    }
}
