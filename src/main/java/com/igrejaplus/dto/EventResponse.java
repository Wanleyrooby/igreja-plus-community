package com.igrejaplus.dto;

import java.time.Instant;
import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String description,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Instant createdAt
) {
}
