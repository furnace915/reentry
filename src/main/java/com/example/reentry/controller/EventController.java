package com.example.reentry.controller;

import com.example.reentry.dto.CreateEventRequest;
import com.example.reentry.dto.EventResponse;
import com.example.reentry.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Tag(name = "Events", description = "Create, view, update, and filter calendar events")
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(
            summary = "List events",
            description = "Returns every event associated with the given family member id, " +
                    "or all events if no familyMemberId is provided."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Events returned (possibly empty)")
    })
    @GetMapping
    public ResponseEntity<List<EventResponse>> getEvents(
            @Parameter(description = "Id of the family member to filter events by")
            @RequestParam(required = false) UUID familyMemberId) {
        if (familyMemberId != null) {
            return ResponseEntity.ok(eventService.getEventsForFamilyMember(familyMemberId));
        }
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @Operation(
            summary = "Create a calendar event",
            description = "Creates a new calendar event with a name, optional description, and a start/end time."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Event created",
                    content = @Content(schema = @Schema(implementation = EventResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed, or end time is before start time",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
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

    @Operation(
            summary = "Get a calendar event by id",
            description = "Returns a single event by its id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event found",
                    content = @Content(schema = @Schema(implementation = EventResponse.class))),
            @ApiResponse(responseCode = "404", description = "No event exists with the given id",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<EventResponse> getEventById(
            @Parameter(description = "Id of the event to retrieve", required = true)
            @PathVariable UUID id) {

        var foundEvent = eventService.getEventById(id);
        return ResponseEntity.ok(foundEvent);
    }

    @Operation(
            summary = "Replace a calendar event",
            description = "Fully replaces an existing event's name, description, and start/end time. " +
                    "This is a full replace (PUT semantics), not a partial update."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event updated",
                    content = @Content(schema = @Schema(implementation = EventResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed, or end time is before start time",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "No event exists with the given id",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @Parameter(description = "Id of the event to update", required = true)
            @PathVariable UUID id,
            @RequestBody @Valid CreateEventRequest request) {

        var updatedEvent = eventService.updateEvent(id, request);
        return ResponseEntity.ok(updatedEvent);
    }
}
