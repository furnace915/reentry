package com.example.reentry.acceptance;

import com.example.reentry.dto.CreateEventRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CreateCalendarEventAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCalendarEventForFamily() throws Exception {
        LocalDateTime startTime = LocalDateTime.of(2026, 8, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2026, 8, 1, 11, 0);
        CreateEventRequest request = new CreateEventRequest(
                "Dentist appointment",
                "Annual checkup",
                startTime,
                endTime);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/events/")))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Dentist appointment"))
                .andExpect(jsonPath("$.description").value("Annual checkup"));
    }
}
