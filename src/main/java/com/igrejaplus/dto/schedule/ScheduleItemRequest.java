package com.igrejaplus.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ScheduleItemRequest(

        @NotNull
        Long memberId,

        @NotBlank
        String ministryRole
) {
}
