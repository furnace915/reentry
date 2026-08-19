package com.example.reentry.repository;

import com.example.reentry.model.Event;
import com.example.reentry.model.FamilyMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Test
    void shouldReturnAllEventsRegardlessOfFamilyMemberAssignment() {
        FamilyMember mom = familyMemberRepository.save(new FamilyMember("Mom"));

        Event assignedEvent = new Event(
                "Mom's dentist appointment",
                "Annual checkup",
                LocalDateTime.of(2026, 8, 1, 10, 0),
                LocalDateTime.of(2026, 8, 1, 11, 0));
        assignedEvent.getFamilyMembers().add(mom);
        Event savedAssignedEvent = eventRepository.save(assignedEvent);

        Event unassignedEvent = eventRepository.save(new Event(
                "Family dinner",
                "Everyone at the table",
                LocalDateTime.of(2026, 8, 6, 18, 0),
                LocalDateTime.of(2026, 8, 6, 19, 0)));

        assertThat(eventRepository.findAll())
                .extracting(Event::getId)
                .contains(savedAssignedEvent.getId(), unassignedEvent.getId());
    }

    @Test
    void shouldReturnEventsAssignedToFamilyMemberAndFamilyWideEvents() {
        FamilyMember mom = familyMemberRepository.save(new FamilyMember("Mom"));
        FamilyMember dad = familyMemberRepository.save(new FamilyMember("Dad"));

        Event momsEvent = new Event(
                "Mom's dentist appointment",
                "Annual checkup",
                LocalDateTime.of(2026, 8, 10, 9, 0),
                LocalDateTime.of(2026, 8, 10, 10, 0));
        momsEvent.getFamilyMembers().add(mom);
        Event savedMomsEvent = eventRepository.save(momsEvent);

        Event dadsEvent = new Event(
                "Dad's work meeting",
                "Quarterly review",
                LocalDateTime.of(2026, 8, 10, 11, 0),
                LocalDateTime.of(2026, 8, 10, 12, 0));
        dadsEvent.getFamilyMembers().add(dad);
        eventRepository.save(dadsEvent);

        Event familyDinner = new Event(
                "Family dinner",
                "Everyone at the table",
                LocalDateTime.of(2026, 8, 10, 18, 0),
                LocalDateTime.of(2026, 8, 10, 19, 0));
        familyDinner.setFamilyEvent(true);
        Event savedFamilyDinner = eventRepository.save(familyDinner);

        assertThat(eventRepository.findVisibleToFamilyMember(mom.getId()))
                .extracting(Event::getId)
                .containsExactlyInAnyOrder(savedMomsEvent.getId(), savedFamilyDinner.getId());
    }

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
