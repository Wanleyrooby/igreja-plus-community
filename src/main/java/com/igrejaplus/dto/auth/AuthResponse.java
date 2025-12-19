package com.igrejaplus.dto.auth;

import com.igrejaplus.model.Role;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        Role role
) {
}
