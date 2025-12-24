package com.igrejaplus.mapper;

import com.igrejaplus.dto.schedule.ScheduleItemResponse;
import com.igrejaplus.dto.schedule.ScheduleRequest;
import com.igrejaplus.dto.schedule.ScheduleResponse;
import com.igrejaplus.model.Schedule;
import com.igrejaplus.model.ScheduleItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleMapper {

    private final ScheduleItemMapper scheduleItemMapper;

    public ScheduleResponse toResponse(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getType(),
                schedule.getDate(),
                toItemResponse(schedule.getItems())
        );
    }

    private List<ScheduleItemResponse> toItemResponse(List<ScheduleItem> items) {

        if (items == null) {
            return List.of();
        }

        return items.stream()
                .map(scheduleItemMapper::toResponse)
                .toList();
    }

    public Schedule toEntity(ScheduleRequest dto) {
        Schedule schedule = new Schedule();

        schedule.setType(dto.type());
        schedule.setDate(dto.date());

        if (dto.items() == null) {
            schedule.setItems(new ArrayList<>());
            return schedule;
        }

        List<ScheduleItem> items = dto.items().stream()
                .map(itemDto -> {
                    ScheduleItem item = new ScheduleItem();
                    item.setMinistryRole(itemDto.ministryRole());
                    item.setSchedule(schedule);
                    return item;
                })
                .toList();

        schedule.setItems(items);
        return schedule;
    }
}
