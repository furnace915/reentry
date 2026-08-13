package com.example.reentry.mapper;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import com.example.reentry.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event toEntity(CreateEventRequest request) {
        return toEntity(request, new Event(request.name(), request.description(), request.startTime(), request.endTime()));
    }

    public Event toEntity(CreateEventRequest request, Event target) {
        target.setName(request.name());
        target.setDescription(request.description());
        target.setStartTime(request.startTime());
        target.setEndTime(request.endTime());
        target.setFamilyEvent(request.familyEvent());

        return target;
    }

    public EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getStartTime(),
                event.getEndTime(),
                event.isFamilyEvent());
    }
}
