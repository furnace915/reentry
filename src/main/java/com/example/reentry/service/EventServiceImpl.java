package com.example.reentry.service;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import com.example.reentry.exception.EventNotFoundException;
import com.example.reentry.model.Event;
import com.example.reentry.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        Optional<Event> foundEvent = eventRepository.findById(eventId);
        
        if (foundEvent.isPresent()) {
            return new EventResponse(
                    foundEvent.get().getId(),
                    foundEvent.get().getName(),
                    foundEvent.get().getDescription(),
                    foundEvent.get().getStartTime(),
                    foundEvent.get().getEndTime());

        }
        throw new EventNotFoundException("Event with id " + eventId + " not found");
    }
}
