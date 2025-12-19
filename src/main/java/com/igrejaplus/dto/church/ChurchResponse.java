package com.igrejaplus.dto.church;

import java.time.Instant;

public record ChurchResponse(
        Long id,
        String name,
        String slug,
        String plan,
        Instant createdAt
) {
}
