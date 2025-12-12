package com.igrejaplus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 6, max = 100)
        String password,

        @NotBlank
        String fullname,

        @NotBlank
        String role,

        Long churchId
) {
}
