package com.igrejaplus.dto.schedule;

public record ScheduleItemResponse(
        Long id,
        Long scheduleId,
        Long memberId,
        String memberName,
        String ministryRole
) {
}
