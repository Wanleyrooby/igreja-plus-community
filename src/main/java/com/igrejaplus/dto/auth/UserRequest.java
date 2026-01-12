package com.igrejaplus.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        /*@Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{6,}$",
                message = "Senha deve conter maiúscula, minúscula, número e símbolo"
        )*/
        String password,

        @NotBlank
        String fullname
) {
}
