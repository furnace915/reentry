package com.example.reentry.service;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import com.example.reentry.exception.EventNotFoundException;
import com.example.reentry.mapper.EventMapper;
import com.example.reentry.model.Event;
import com.example.reentry.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Test
    void shouldRejectEventWhenEndTimeIsBeforeStartTime() {
        LocalDateTime startTime = LocalDateTime.of(2026, 8, 1, 11, 0);
        LocalDateTime endTime = LocalDateTime.of(2026, 8, 1, 10, 0);
        CreateEventRequest request = new CreateEventRequest(
                "Dentist appointment",
                "Annual checkup",
                startTime,
                endTime,
                false);

        assertThrows(IllegalArgumentException.class, () -> eventService.createEvent(request));
    }

    @Test
    void shouldCreateEvent() {
        CreateEventRequest request = new CreateEventRequest(
                "Dentist appointment",
                "Annual checkup",
                LocalDateTime.of(2026, 8, 1, 10, 0),
                LocalDateTime.of(2026, 8, 1, 11, 0),
                false);
        Event eventToSave = mock(Event.class);
        Event savedEvent = mock(Event.class);
        EventResponse expectedResponse = new EventResponse(
                UUID.randomUUID(), "Dentist appointment", "Annual checkup", request.startTime(), request.endTime(), false);

        when(eventMapper.toEntity(request)).thenReturn(eventToSave);
        when(eventRepository.save(eventToSave)).thenReturn(savedEvent);
        when(eventMapper.toResponse(savedEvent)).thenReturn(expectedResponse);

        EventResponse actual = eventService.createEvent(request);

        assertThat(actual).isEqualTo(expectedResponse);
        verify(eventMapper).toEntity(request);
        verify(eventRepository).save(eventToSave);
        verify(eventMapper).toResponse(savedEvent);
    }

    @Test
    void shouldReturnEventWhenFound() {
        UUID eventId = UUID.randomUUID();
        Event existingEvent = mock(Event.class);
        EventResponse expectedResponse = new EventResponse(
                eventId, "Dentist appointment", "Annual checkup",
                LocalDateTime.of(2026, 8, 1, 10, 0), LocalDateTime.of(2026, 8, 1, 11, 0), false);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventMapper.toResponse(existingEvent)).thenReturn(expectedResponse);

        EventResponse actual = eventService.getEventById(eventId);

        assertThat(actual).isEqualTo(expectedResponse);
        verify(eventMapper).toResponse(existingEvent);
    }

    @Test
    void shouldThrowEventNotFoundExceptionWhenEventDoesNotExist() {
        UUID eventId = UUID.randomUUID();

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.getEventById(eventId));
    }

    @Test
    void shouldUpdateExistingEvent() {
        UUID eventId = UUID.randomUUID();
        Event existingEvent = mock(Event.class);
        Event updatedEvent = mock(Event.class);
        CreateEventRequest updateRequest = new CreateEventRequest(
                "Dentist appointment (rescheduled)",
                "Annual checkup - moved a day",
                LocalDateTime.of(2026, 8, 2, 14, 0),
                LocalDateTime.of(2026, 8, 2, 15, 0),
                false);
        EventResponse expectedResponse = new EventResponse(
                eventId, updateRequest.name(), updateRequest.description(),
                updateRequest.startTime(), updateRequest.endTime(), false);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventMapper.toEntity(updateRequest, existingEvent)).thenReturn(updatedEvent);
        when(eventRepository.save(updatedEvent)).thenReturn(updatedEvent);
        when(eventMapper.toResponse(updatedEvent)).thenReturn(expectedResponse);

        EventResponse actual = eventService.updateEvent(eventId, updateRequest);

        assertThat(actual).isEqualTo(expectedResponse);
        verify(eventMapper).toEntity(updateRequest, existingEvent);
        verify(eventRepository).save(updatedEvent);
        verify(eventMapper).toResponse(updatedEvent);
    }

    @Test
    void shouldThrowEventNotFoundExceptionWhenUpdatingNonexistentEvent() {
        UUID eventId = UUID.randomUUID();
        CreateEventRequest updateRequest = new CreateEventRequest(
                "Dentist appointment (rescheduled)",
                "Annual checkup - moved a day",
                LocalDateTime.of(2026, 8, 2, 14, 0),
                LocalDateTime.of(2026, 8, 2, 15, 0),
                false);

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.updateEvent(eventId, updateRequest));
    }
}
