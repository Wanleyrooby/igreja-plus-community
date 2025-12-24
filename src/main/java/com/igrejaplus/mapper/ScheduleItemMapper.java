package com.igrejaplus.mapper;

import com.igrejaplus.dto.schedule.ScheduleItemResponse;
import com.igrejaplus.model.ScheduleItem;
import org.springframework.stereotype.Component;

@Component
public class ScheduleItemMapper {

    public ScheduleItemResponse toResponse(ScheduleItem item) {
        return new ScheduleItemResponse(
                item.getId(),
                item.getSchedule().getId(),
                item.getMember().getId(),
                item.getMember().getName(),
                item.getMinistryRole()
        );
    }
}
