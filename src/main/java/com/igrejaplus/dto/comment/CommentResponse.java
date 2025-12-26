package com.igrejaplus.dto.comment;

import java.time.Instant;

public record CommentResponse(
        Long id,
        String content,
        Boolean approved,
        Long memberId,
        String memberName,
        Long eventId,
        Instant createdAt
) {
}
