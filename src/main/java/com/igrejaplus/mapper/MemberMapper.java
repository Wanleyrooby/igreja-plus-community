package com.igrejaplus.mapper;

import com.igrejaplus.dto.member.MemberResponse;
import com.igrejaplus.model.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberResponse toResponse(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getUser().getEmail(),
                member.getPhone(),
                member.getBirthday()
        );
    }
}
