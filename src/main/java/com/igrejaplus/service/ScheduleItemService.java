package com.igrejaplus.service;

import com.igrejaplus.dto.schedule.ScheduleItemRequest;
import com.igrejaplus.dto.schedule.ScheduleItemResponse;
import com.igrejaplus.exception.ResourceNotFoundException;
import com.igrejaplus.mapper.ScheduleItemMapper;
import com.igrejaplus.model.Member;
import com.igrejaplus.model.Schedule;
import com.igrejaplus.model.ScheduleItem;
import com.igrejaplus.repository.MemberRepository;
import com.igrejaplus.repository.ScheduleItemRepository;
import com.igrejaplus.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleItemService {

    private final ScheduleItemRepository scheduleItemRepository;
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final ScheduleItemMapper scheduleItemMapper;


    public ScheduleItemResponse create(Long scheduleId, ScheduleItemRequest request) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Escala não encontrada."));

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado."));

        ScheduleItem item = ScheduleItem.builder()
                .schedule(schedule)
                .member(member)
                .ministryRole(request.ministryRole())
                .build();

        ScheduleItem saved = scheduleItemRepository.save(item);

        return scheduleItemMapper.toResponse(saved);
    }


    public ScheduleItemResponse update(Long itemId, ScheduleItemRequest request) {

        ScheduleItem item = scheduleItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item de escala não encontrado."));

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado."));

        item.setMember(member);
        item.setMinistryRole(request.ministryRole());

        return scheduleItemMapper.toResponse(item);
    }


    public void delete(Long itemId) {
        if (!scheduleItemRepository.existsById(itemId)) {
            throw new ResourceNotFoundException("Item de escala não encontrado.");
        }
        scheduleItemRepository.deleteById(itemId);
    }
}
