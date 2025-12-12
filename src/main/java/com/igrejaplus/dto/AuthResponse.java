package com.igrejaplus.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String fullname,
        String role
) {
}
