package com.igrejaplus.dto.schedule;

import java.time.LocalDate;
import java.util.List;

public record ScheduleResponse(
        Long id,
        String type,
        LocalDate date,
        List<ScheduleItemResponse> items
) {
}
