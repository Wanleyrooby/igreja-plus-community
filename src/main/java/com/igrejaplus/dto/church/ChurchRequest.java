package com.igrejaplus.dto.church;

import jakarta.validation.constraints.NotBlank;

public record ChurchRequest(

        @NotBlank
        String name,

        String slug,
        String plan
) {
}
