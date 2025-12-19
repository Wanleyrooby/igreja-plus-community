package com.igrejaplus.service;

import com.igrejaplus.dto.auth.AuthResponse;
import com.igrejaplus.dto.auth.LoginRequest;
import com.igrejaplus.dto.auth.RefreshTokenRequest;
import com.igrejaplus.dto.auth.UserRequest;
import com.igrejaplus.model.Member;
import com.igrejaplus.model.Role;
import com.igrejaplus.model.User;
import com.igrejaplus.repository.MemberRepository;
import com.igrejaplus.repository.UserRepository;
import com.igrejaplus.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse register(UserRequest dto) {

        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        User user = User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.MEMBER_READ_ONLY)
                .build();

        userRepository.save(user);

        // 🔹 cria perfil automaticamente
        Member member = Member.builder()
                .name(dto.fullname())
                .user(user)
                .build();

        memberRepository.save(member);

        return generateTokens(user);
    }

    public AuthResponse login(LoginRequest dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(), dto.password()
                )
        );

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        return generateTokens(user);
    }

    public AuthResponse refreshToken(RefreshTokenRequest dto) {

        User user = refreshTokenService.validateAndGetUser(dto.refreshToken());

        return generateTokens(user);
    }

    public void logout(String refreshToken) {
        refreshTokenService.revoke(refreshToken);
    }

    private AuthResponse generateTokens(User user) {

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        refreshTokenService.create(
                user,
                refreshToken,
                Instant.now().plusMillis(7 * 24 * 60 * 60 * 1000)
        );

        return new AuthResponse(
                accessToken,
                refreshToken,
                user.getRole()
        );
    }
}

