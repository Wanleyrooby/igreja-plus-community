package com.igrejaplus.controller;

import com.igrejaplus.dto.comment.CommentRequest;
import com.igrejaplus.dto.comment.CommentResponse;
import com.igrejaplus.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid CommentRequest dto) {
        CommentResponse response = commentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<CommentResponse>> findApprovedByEvent(@PathVariable Long eventId) {
        List<CommentResponse> response = commentService.findApprovedByEvent(eventId);
        return ResponseEntity.ok(response);
    }

    // ADMIN
    @PatchMapping("/{commentId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommentResponse> approve(@PathVariable Long commentId) {
        CommentResponse response = commentService.approve(commentId);
        return ResponseEntity.ok(response);
    }

    // ADMIN
    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}
