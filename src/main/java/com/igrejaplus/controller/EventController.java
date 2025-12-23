package com.igrejaplus.controller;

import com.igrejaplus.dto.EventResponse;
import com.igrejaplus.dto.EventsRequest;
import com.igrejaplus.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> create(@RequestBody @Valid EventsRequest dto) {
        EventResponse eventResponse = eventService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EventResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(eventService.findAll(pageable));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Page<EventResponse>> findUpComing(Pageable pageable) {
        return ResponseEntity.ok(eventService.findUpcoming(pageable));
    }

    // ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> Update(@PathVariable Long id,@RequestBody @Valid EventsRequest dto) {
        return ResponseEntity.ok(eventService.update(id, dto));
    }

    // ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
