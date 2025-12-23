package com.igrejaplus.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record EventsRequest(

        @NotBlank
        String title,

        String description,
        LocalDateTime startAt,
        LocalDateTime endAt
) {
}
