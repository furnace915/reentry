package com.example.reentry.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
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

    // Required by JPA/Hibernate, which instantiates entities via reflection when loading from the database.
    protected Event() {
    }

    public Event(String name, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
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
}
