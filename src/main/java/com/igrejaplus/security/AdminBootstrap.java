package com.igrejaplus.security;

import com.igrejaplus.model.Role;
import com.igrejaplus.model.User;
import com.igrejaplus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBootstrap implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean adminExists =
                userRepository.existsByRole(Role.ADMIN);

        if (adminExists) {
            return;
        }

        User admin = User.builder()
                .email("admin@igreja.com")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);

        System.out.println("ADMIN criado automaticamente");
    }
}
