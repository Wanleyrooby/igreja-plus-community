package com.igrejaplus.dto.event;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventsRequest(

        @NotBlank
        String title,

        String description,

        @Future
        @NotNull
        LocalDateTime startAt,

        @Future
        LocalDateTime endAt
) {
}
