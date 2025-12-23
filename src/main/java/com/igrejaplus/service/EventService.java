package com.igrejaplus.service;

import com.igrejaplus.dto.EventResponse;
import com.igrejaplus.dto.EventsRequest;
import com.igrejaplus.mapper.EventMapper;
import com.igrejaplus.model.Event;
import com.igrejaplus.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventResponse create(EventsRequest dto) {
        validateDates(dto.startAt(), dto.endAt());

        Event event = eventMapper.toEntity(dto);
        Event saved = eventRepository.save(event);

        return eventMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public EventResponse findById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        return eventMapper.toResponse(event);
    }

    @Transactional(readOnly = true)
    public Page<EventResponse> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(eventMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<EventResponse> findUpcoming(Pageable pageable) {
        return eventRepository
                .findByStartAtAfter(LocalDateTime.now(), pageable)
                .map(eventMapper::toResponse);
    }

    public EventResponse update(Long id, EventsRequest dto) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        if (dto.title() != null && !dto.title().isBlank()) {
            event.setTitle(dto.title());
        }

        if (dto.description() != null) {
            event.setDescription(dto.description());
        }

        if (dto.startAt() != null) {
            event.setStartAt(dto.startAt());
        }

        if (dto.endAt() != null) {
            event.setEndAt(dto.endAt());
        }

        validateDates(event.getStartAt(), event.getEndAt());

        return eventMapper.toResponse(event);
    }

    public void delete(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Evento não encontrado");
        }
        eventRepository.deleteById(id);
    }

    // ================= REGRA DE NEGÓCIO =================

    private void validateDates(LocalDateTime start, LocalDateTime end) {

        LocalDateTime now = LocalDateTime.now();

        if (start == null) {
            throw new IllegalArgumentException("Data inicial é obrigatória.");
        }

        if (start.isBefore(now)) {
            throw new IllegalArgumentException("O evento deve começar no futuro.");
        }

        if (end != null && end.isBefore(start)) {
            throw new IllegalArgumentException("A data final não pode ser antes da inicial.");
        }
    }
}
