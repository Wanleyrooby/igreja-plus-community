package com.igrejaplus.dto.member;

import java.time.LocalDate;

public record MemberResponse(
        Long id,
        String name,
        String email,
        String phone,
        LocalDate birthday
) {
}
