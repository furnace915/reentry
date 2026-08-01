package com.example.reentry.controller;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import com.example.reentry.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;


    @PostMapping("/api/events")
    public ResponseEntity<EventResponse> createEvent(@RequestBody @Valid CreateEventRequest request) {

        EventResponse savedEvent = eventService.createEvent(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(savedEvent.id())
                .toUri();

        return ResponseEntity.created(location).body(savedEvent);
    }
}
