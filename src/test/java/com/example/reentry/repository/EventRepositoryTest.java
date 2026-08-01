package com.example.reentry.repository;

import com.example.reentry.model.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void shouldSaveAndFindEventById() {
        LocalDateTime startTime = LocalDateTime.of(2026, 8, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2026, 8, 1, 11, 0);
        Event event = new Event(
                "Dentist appointment",
                "Annual checkup",
                startTime,
                endTime);

        Event savedEvent = eventRepository.save(event);

        Optional<Event> foundEvent = eventRepository.findById(savedEvent.getId());

        assertThat(foundEvent).isPresent();
        assertThat(foundEvent.get().getId()).isEqualTo(savedEvent.getId());
        assertThat(foundEvent.get().getName()).isEqualTo("Dentist appointment");
        assertThat(foundEvent.get().getDescription()).isEqualTo("Annual checkup");
        assertThat(foundEvent.get().getStartTime()).isEqualTo(startTime);
        assertThat(foundEvent.get().getEndTime()).isEqualTo(endTime);
    }
}
