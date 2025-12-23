package com.igrejaplus.mapper;

import com.igrejaplus.dto.event.EventResponse;
import com.igrejaplus.dto.event.EventsRequest;
import com.igrejaplus.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event toEntity(EventsRequest dto) {
        Event event = new Event();

        event.setTitle(dto.title());
        event.setDescription(dto.description());
        event.setStartAt(dto.startAt());
        event.setEndAt(dto.endAt());

        return event;
    }

    public EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStartAt(),
                event.getEndAt(),
                event.getCreatedAt()
        );
    }
}
