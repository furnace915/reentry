package com.example.reentry.controller;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import com.example.reentry.exception.EventNotFoundException;
import com.example.reentry.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EventService eventService;

    @Test
    void shouldReturn201WithLocationHeaderAndBodyWhenEventCreated() throws Exception {
        LocalDateTime startTime = LocalDateTime.of(2026, 8, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2026, 8, 1, 11, 0);
        CreateEventRequest request = new CreateEventRequest(
                "Dentist appointment",
                "Annual checkup",
                startTime,
                endTime);
        UUID savedEventId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        EventResponse savedEvent = new EventResponse(
                savedEventId,
                "Dentist appointment",
                "Annual checkup",
                startTime,
                endTime);

        when(eventService.createEvent(any(CreateEventRequest.class))).thenReturn(savedEvent);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/events/" + savedEventId))
                .andExpect(jsonPath("$.id").value(savedEventId.toString()))
                .andExpect(jsonPath("$.name").value("Dentist appointment"))
                .andExpect(jsonPath("$.description").value("Annual checkup"));
    }

    @Test
    void shouldReturn400WhenTitleIsBlank() throws Exception {
        LocalDateTime startTime = LocalDateTime.of(2026, 8, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2026, 8, 1, 11, 0);
        CreateEventRequest request = new CreateEventRequest(
                "",
                "Annual checkup",
                startTime,
                endTime);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(eventService, never()).createEvent(any(CreateEventRequest.class));
    }

    @Test
    void shouldReturn400WhenStartTimeIsBlank() throws Exception {
        LocalDateTime startTime = null;
        LocalDateTime endTime = LocalDateTime.of(2026, 8, 1, 11, 0);
        CreateEventRequest request = new CreateEventRequest(
                "Valid Title",
                "Annual checkup",
                startTime,
                endTime);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(eventService, never()).createEvent(any(CreateEventRequest.class));
    }

    @Test
    void shouldReturn400WhenEndTimeIsNull() throws Exception {
        LocalDateTime startTime = LocalDateTime.of(2026, 8, 1, 10, 0);
        LocalDateTime endTime = null;
        CreateEventRequest request = new CreateEventRequest(
                "Valid Title",
                "Annual checkup",
                startTime,
                endTime);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(eventService, never()).createEvent(any(CreateEventRequest.class));
    }

    @Test
    void shouldReturn400WhenServiceRejectsInvalidTimeRange() throws Exception {
        LocalDateTime startTime = LocalDateTime.of(2026, 8, 1, 11, 0);
        LocalDateTime endTime = LocalDateTime.of(2026, 8, 1, 10, 0);
        CreateEventRequest request = new CreateEventRequest(
                "Valid Title",
                "Annual checkup",
                startTime,
                endTime);

        when(eventService.createEvent(any(CreateEventRequest.class)))
                .thenThrow(new IllegalArgumentException("End time cannot be before start time"));

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn200WithEventWhenFound() throws Exception {
        UUID eventId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        LocalDateTime startTime = LocalDateTime.of(2026, 8, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2026, 8, 1, 11, 0);
        EventResponse existingEvent = new EventResponse(
                eventId,
                "Dentist appointment",
                "Annual checkup",
                startTime,
                endTime);

        when(eventService.getEventById(eventId)).thenReturn(existingEvent);

        mockMvc.perform(get("/api/events/{id}", eventId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(eventId.toString()))
                .andExpect(jsonPath("$.name").value("Dentist appointment"))
                .andExpect(jsonPath("$.description").value("Annual checkup"));
    }

    @Test
    void shouldReturn404WhenEventNotFound() throws Exception {
        UUID eventId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        String expectedMessage = "Event not found: " + eventId;

        when(eventService.getEventById(eventId)).thenThrow(new EventNotFoundException(expectedMessage));

        mockMvc.perform(get("/api/events/{id}", eventId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedMessage));
    }

    @Test
    void shouldThrow404NotFoundExceptionWhenEventNotFoundExceptionThrown() throws Exception {
        UUID eventId = UUID.randomUUID();
        String expectedMessage = "Event not found: " + eventId;

        CreateEventRequest eventRequest = new CreateEventRequest(
                "Updated Event",
                "Updated Description",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );

        when(eventService.updateEvent(eventId, eventRequest)).thenThrow(new EventNotFoundException(expectedMessage));

        mockMvc.perform(put("/api/events/{id}", eventId)
                        .content(objectMapper.writeValueAsString(eventRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedMessage));
    }

    @Test
    void shouldReturn200WithUpdatedEventWhenEventUpdated() throws Exception {
        UUID eventId = UUID.randomUUID();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        CreateEventRequest updateRequest = new CreateEventRequest(
                "Updated Event",
                "Updated Description",
                startTime,
                endTime);
        EventResponse updatedEvent = new EventResponse(
                eventId,
                "Updated Event",
                "Updated Description",
                startTime,
                endTime);

        when(eventService.updateEvent(eventId, updateRequest)).thenReturn(updatedEvent);

        mockMvc.perform(put("/api/events/{id}", eventId)
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(eventId.toString()))
                .andExpect(jsonPath("$.name").value("Updated Event"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    void shouldReturnEventsForFamilyMember() throws Exception {
        UUID familyMemberId = UUID.randomUUID();
        EventResponse event = new EventResponse(
                UUID.randomUUID(),
                "Family dinner",
                "Everyone at the table",
                LocalDateTime.of(2026, 8, 6, 18, 0),
                LocalDateTime.of(2026, 8, 6, 19, 0));

        when(eventService.getEventsForFamilyMember(familyMemberId)).thenReturn(List.of(event));

        mockMvc.perform(get("/api/events").param("familyMemberId", familyMemberId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Family dinner"))
                .andExpect(jsonPath("$[0].description").value("Everyone at the table"));
    }
}
