package com.igrejaplus.service;

import com.igrejaplus.dto.AuthResponse;
import com.igrejaplus.dto.LoginRequest;
import com.igrejaplus.dto.RefreshTokenRequest;
import com.igrejaplus.dto.UserRequest;
import com.igrejaplus.model.Church;
import com.igrejaplus.model.User;
import com.igrejaplus.repository.ChurchRepository;
import com.igrejaplus.repository.UserRepository;
import com.igrejaplus.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ChurchRepository churchRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse register(UserRequest dto) {

        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException(("Email já cadastrado."));
        }

        Church church = null;
        if (dto.churchId() != null) {
            church = churchRepository.findById(dto.churchId())
                    .orElseThrow(() -> new RuntimeException("Igreja não encontrada."));
        }

        String normalizedRole = dto.role() == null ? "MEMBER"
                : dto.role().trim().toUpperCase().replace("ROLE_", "");

        User user = User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password())) // importante: encriptar senha
                .fullname(dto.fullname())
                .role(normalizedRole) // salva "ADMIN", não "ROLE_ADMIN"
                .church(church)
                .build();

        userRepository.save(user);

        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new AuthResponse(accessToken, refreshToken, user.getFullname(), user.getRole());

    }


    public AuthResponse login(LoginRequest dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new AuthResponse(accessToken, refreshToken, user.getFullname(), user.getRole());
    }

    public AuthResponse refreshToken(RefreshTokenRequest dto) {

        String refreshToken = dto.refreshToken();

        if (!jwtUtil.isRefreshTokenValid(refreshToken)) {
            throw new RuntimeException("Refresh token inválido ou expirado.");
        }

        String email = jwtUtil.extractEmailFromRefresh(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        String newAccessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                user.getFullname(),
                user.getRole()
        );
    }

}
