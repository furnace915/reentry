package com.example.reentry.repository;

import com.example.reentry.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByFamilyMembers_Id(UUID familyMemberId);
}
