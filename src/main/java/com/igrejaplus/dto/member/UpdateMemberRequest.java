package com.igrejaplus.dto.member;

import java.time.LocalDate;

public record UpdateMemberRequest(
        String name,
        String email,
        String phone,
        LocalDate birthday
) {
}
