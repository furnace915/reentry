package com.example.reentry.acceptance;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.model.Event;
import com.example.reentry.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EventAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventRepository eventRepository;

    @Test
    void shouldReturnCalendarEventById() throws Exception {
        LocalDateTime startTime = LocalDateTime.of(2026, 8, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2026, 8, 1, 11, 0);
        CreateEventRequest request = new CreateEventRequest(
                "Dentist appointment",
                "Annual checkup",
                startTime,
                endTime,
                false);

        String createResponse = mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String eventId = objectMapper.readTree(createResponse).get("id").asText();

        mockMvc.perform(get("/api/events/{id}", eventId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(eventId))
                .andExpect(jsonPath("$.name").value("Dentist appointment"))
                .andExpect(jsonPath("$.description").value("Annual checkup"));
    }

    @Test
    void shouldUpdateCalendarEventForFamily() throws Exception {
        Event existingEvent = new Event(
                "Dentist appointment",
                "Annual checkup",
                LocalDateTime.of(2026, 8, 1, 10, 0),
                LocalDateTime.of(2026, 8, 1, 11, 0));
        Event savedEvent = eventRepository.save(existingEvent);

        CreateEventRequest updateRequest = new CreateEventRequest(
                "Dentist appointment (rescheduled)",
                "Annual checkup - moved a day",
                LocalDateTime.of(2026, 8, 2, 14, 0),
                LocalDateTime.of(2026, 8, 2, 15, 0),
                false);

        mockMvc.perform(put("/api/events/{id}", savedEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedEvent.getId().toString()))
                .andExpect(jsonPath("$.name").value("Dentist appointment (rescheduled)"))
                .andExpect(jsonPath("$.description").value("Annual checkup - moved a day"));

        Optional<Event> updatedEvent = eventRepository.findById(savedEvent.getId());

        assertThat(updatedEvent).isPresent();
        assertThat(updatedEvent.get())
                .returns("Dentist appointment (rescheduled)", Event::getName)
                .returns("Annual checkup - moved a day", Event::getDescription)
                .returns(LocalDateTime.of(2026, 8, 2, 14, 0), Event::getStartTime)
                .returns(LocalDateTime.of(2026, 8, 2, 15, 0), Event::getEndTime);
    }
}
