package com.igrejaplus.controller;

import com.igrejaplus.dto.schedule.ScheduleItemRequest;
import com.igrejaplus.dto.schedule.ScheduleItemResponse;
import com.igrejaplus.service.ScheduleItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules/{scheduleId}/items")
@RequiredArgsConstructor
public class ScheduleItemController {

    private final ScheduleItemService scheduleItemService;

    // ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScheduleItemResponse> create(@PathVariable Long scheduleId, @RequestBody @Valid ScheduleItemRequest request) {
        ScheduleItemResponse response = scheduleItemService.create(scheduleId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ADMIN
    @PutMapping("/{itemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScheduleItemResponse> update(@PathVariable Long itemId, @RequestBody @Valid ScheduleItemRequest request) {
        return ResponseEntity.ok(scheduleItemService.update(itemId, request));
    }

    // ADMIN
    @DeleteMapping("/{itemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long itemId) {
        scheduleItemService.delete(itemId);
        return ResponseEntity.noContent().build();
    }
}
