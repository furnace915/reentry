package com.example.reentry.repository;

import com.example.reentry.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT DISTINCT e FROM Event e LEFT JOIN e.familyMembers fm " +
           "WHERE e.familyEvent = true OR fm.id = :familyMemberId")
    List<Event> findVisibleToFamilyMember(@Param("familyMemberId") UUID familyMemberId);
}
