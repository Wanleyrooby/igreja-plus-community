package com.igrejaplus.dto.schedule;

import java.time.LocalDate;
import java.util.List;

public record ScheduleRequest(
        String type,
        LocalDate date,
        List<ScheduleItemRequest> items
) {
}
