package com.igrejaplus.dto;

import java.time.Instant;

public record ChurchResponse(
        Long id,
        String name,
        String slug,
        String plan,
        Instant createdAt
) {
}
