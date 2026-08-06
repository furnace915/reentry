package com.example.reentry.controller;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import com.example.reentry.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getEventsForFamilyMember(@RequestParam UUID familyMemberId) {
        return ResponseEntity.ok(eventService.getEventsForFamilyMember(familyMemberId));
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody @Valid CreateEventRequest request) {

        EventResponse savedEvent = eventService.createEvent(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(savedEvent.id())
                .toUri();

        return ResponseEntity.created(location).body(savedEvent);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable UUID id) {

        var foundEvent = eventService.getEventById(id);
        return ResponseEntity.ok(foundEvent);
    }

    @PutMapping("{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable UUID id, @RequestBody @Valid CreateEventRequest request) {

        var updatedEvent = eventService.updateEvent(id, request);
        return ResponseEntity.ok(updatedEvent);
    }
}
