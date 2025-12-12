package com.igrejaplus.dto;

import jakarta.validation.constraints.NotBlank;

public record ChurchRequest(

        @NotBlank
        String name,

        String slug,
        String plan
) {
}
