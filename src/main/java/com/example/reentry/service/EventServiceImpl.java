package com.example.reentry.service;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import com.example.reentry.model.Event;
import com.example.reentry.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public EventResponse createEvent(CreateEventRequest request) {
        if (request.endTime().isBefore(request.startTime())) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }

        Event event = new Event(request.name(), request.description(), request.startTime(), request.endTime());
        Event savedEvent = eventRepository.save(event);

        return new EventResponse(
                savedEvent.getId(),
                savedEvent.getName(),
                savedEvent.getDescription(),
                savedEvent.getStartTime(),
                savedEvent.getEndTime());
    }
}
