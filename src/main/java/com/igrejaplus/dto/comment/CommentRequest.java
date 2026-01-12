package com.igrejaplus.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(

        @NotBlank
        String content,

        @NotNull
        Long eventId
) {
}
