package com.example.reentry.mapper;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import com.example.reentry.model.Event;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EventMapperTest {

    private final EventMapper eventMapper = new EventMapper();

    @Test
    void shouldCreateNewEntityWhenNoExistingEventProvided() {
        CreateEventRequest request = new CreateEventRequest(
                "Dentist appointment",
                "Annual checkup",
                LocalDateTime.of(2026, 8, 1, 10, 0),
                LocalDateTime.of(2026, 8, 1, 11, 0));

        Event actual = eventMapper.toEntity(request);

        assertThat(actual)
                .returns(request.name(), Event::getName)
                .returns(request.description(), Event::getDescription)
                .returns(request.startTime(), Event::getStartTime)
                .returns(request.endTime(), Event::getEndTime);
    }

    @Test
    void shouldUpdateExistingEntityInPlaceWhenProvided() {
        UUID existingId = UUID.randomUUID();
        Event existingEvent = new Event(
                "Dentist appointment",
                "Annual checkup",
                LocalDateTime.of(2026, 8, 1, 10, 0),
                LocalDateTime.of(2026, 8, 1, 11, 0));
        ReflectionTestUtils.setField(existingEvent, "id", existingId);

        CreateEventRequest updateRequest = new CreateEventRequest(
                "Dentist appointment (rescheduled)",
                "Annual checkup - moved a day",
                LocalDateTime.of(2026, 8, 2, 14, 0),
                LocalDateTime.of(2026, 8, 2, 15, 0));

        Event actual = eventMapper.toEntity(updateRequest, existingEvent);

        assertThat(actual)
                .isSameAs(existingEvent)
                .returns(existingId, Event::getId)
                .returns(updateRequest.name(), Event::getName)
                .returns(updateRequest.description(), Event::getDescription)
                .returns(updateRequest.startTime(), Event::getStartTime)
                .returns(updateRequest.endTime(), Event::getEndTime);
    }

    @Test
    void shouldMapEntityToResponse() {
        UUID eventId = UUID.randomUUID();
        Event event = new Event(
                "Dentist appointment",
                "Annual checkup",
                LocalDateTime.of(2026, 8, 1, 10, 0),
                LocalDateTime.of(2026, 8, 1, 11, 0));
        ReflectionTestUtils.setField(event, "id", eventId);

        EventResponse actual = eventMapper.toResponse(event);

        assertThat(actual)
                .returns(eventId, EventResponse::id)
                .returns(event.getName(), EventResponse::name)
                .returns(event.getDescription(), EventResponse::description)
                .returns(event.getStartTime(), EventResponse::startTime)
                .returns(event.getEndTime(), EventResponse::endTime);
    }
}
