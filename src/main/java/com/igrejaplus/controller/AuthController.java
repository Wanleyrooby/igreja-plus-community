package com.igrejaplus.controller;

import com.igrejaplus.dto.AuthResponse;
import com.igrejaplus.dto.LoginRequest;
import com.igrejaplus.dto.RefreshTokenRequest;
import com.igrejaplus.dto.UserRequest;
import com.igrejaplus.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserRequest dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody @Valid RefreshTokenRequest dto) {
        return ResponseEntity.ok(authService.refreshToken(dto));
    }
}
