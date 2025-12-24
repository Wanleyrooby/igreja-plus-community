package com.igrejaplus.controller;

import com.igrejaplus.dto.schedule.ScheduleRequest;
import com.igrejaplus.dto.schedule.ScheduleResponse;
import com.igrejaplus.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScheduleResponse> create(@RequestBody @Valid ScheduleRequest dto) {
        ScheduleResponse response = scheduleService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findById(@PathVariable Long id) {
        ScheduleResponse response = scheduleService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> findAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    // ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScheduleResponse> update(@PathVariable Long id, @RequestBody @Valid ScheduleRequest dto) {
        return ResponseEntity.ok(scheduleService.update(id, dto));
    }

    // ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
