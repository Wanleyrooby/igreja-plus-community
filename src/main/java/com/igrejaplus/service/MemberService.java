package com.igrejaplus.service;

import com.igrejaplus.dto.member.MemberResponse;
import com.igrejaplus.dto.member.UpdateMemberRequest;
import com.igrejaplus.mapper.MemberMapper;
import com.igrejaplus.model.Member;
import com.igrejaplus.model.User;
import com.igrejaplus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper mapper;

    @Transactional
    public MemberResponse getMyProfile() {
        Member member = findCurrentMember();
        return mapper.toResponse(member);
    }

    @Transactional
    public MemberResponse updateMyProfile(UpdateMemberRequest dto) {
        Member member = findCurrentMember();
        User user = member.getUser();

        if (dto.name() != null && !dto.name().isBlank()) {
            member.setName(dto.name());
        }

        if (dto.phone() != null && !dto.phone().isBlank()) {
            member.setPhone(dto.phone());
        }

        if (dto.birthday() != null) {
            member.setBirthday(dto.birthday());
        }

        if (dto.email() != null && !dto.email().isBlank()) {
            user.setEmail(dto.email());
        }

        // deixa explícito (boa prática)
        memberRepository.save(member);

        return mapper.toResponse(member);
    }

    @Transactional
    public List<MemberResponse> listAllProfiles() {
        return memberRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    private Member findCurrentMember() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return memberRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado."));
    }
}
