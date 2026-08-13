package com.example.reentry.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String name;
    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;
    boolean familyEvent;

    @ManyToMany
    @JoinTable(name = "event_family_member",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "family_member_id"))
    private Collection<FamilyMember> familyMembers;

    protected Event() {
        // Required by JPA/Hibernate, which instantiates entities via reflection when loading from the database.
    }

    public Event(String name, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.familyMembers = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isFamilyEvent() {
        return familyEvent;
    }

    public void setFamilyEvent(boolean familyEvent) {
        this.familyEvent = familyEvent;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public void setStartTime(@NotNull LocalDateTime localDateTime) {
        this.startTime = localDateTime;
    }

    public void setEndTime(@NotNull LocalDateTime localDateTime) {
        this.endTime = localDateTime;
    }

    public Collection<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }
}
