package com.igrejaplus.service;

import com.igrejaplus.dto.schedule.ScheduleRequest;
import com.igrejaplus.dto.schedule.ScheduleResponse;
import com.igrejaplus.mapper.ScheduleMapper;
import com.igrejaplus.model.Member;
import com.igrejaplus.model.Schedule;
import com.igrejaplus.model.ScheduleItem;
import com.igrejaplus.repository.MemberRepository;
import com.igrejaplus.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final MemberRepository memberRepository;

    public ScheduleResponse create(ScheduleRequest dto) {
        Schedule schedule = scheduleMapper.toEntity(dto);

        for (int i = 0; i < schedule.getItems().size(); i++) {
            ScheduleItem item = schedule.getItems().get(i);
            Long memberId = dto.items().get(i).memberId();

            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Membro não encontrado: " + memberId));

            item.setMember(member);
        }

        Schedule saved = scheduleRepository.save(schedule);
        return scheduleMapper.toResponse(saved);
    }

    public ScheduleResponse findById(Long id) {
        Schedule schedule = scheduleRepository.findByIdWithItems(id)
                .orElseThrow(() -> new RuntimeException("Escala não encontrada."));

        return scheduleMapper.toResponse(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAll() {
        return scheduleRepository.findAllWithItems().stream()
                .map(scheduleMapper::toResponse)
                .toList();
    }

    public ScheduleResponse update(Long id, ScheduleRequest dto) {

        Schedule schedule = scheduleRepository.findByIdWithItems(id)
                .orElseThrow(() -> new RuntimeException("Escala não encontrada."));

        // Atualiza dados simples
        schedule.setType(dto.type());
        schedule.setDate(dto.date());

        // Limpa itens antigos
        schedule.getItems().clear();

        // Recria os itens
        dto.items().forEach(itemDto -> {
            Member member = memberRepository.findById(itemDto.memberId())
                    .orElseThrow(() ->
                            new RuntimeException("Membro não encontrado: " + itemDto.memberId())
                    );

            ScheduleItem item = new ScheduleItem();
            item.setSchedule(schedule);
            item.setMember(member);
            item.setMinistryRole(itemDto.ministryRole());

            schedule.getItems().add(item);
        });

        Schedule saved = scheduleRepository.save(schedule);
        return scheduleMapper.toResponse(saved);
    }

    public void delete(Long id) {

        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Escala não encontrada.");
        }

        scheduleRepository.deleteById(id);
    }

}
