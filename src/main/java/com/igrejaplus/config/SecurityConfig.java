package com.igrejaplus.config;

import com.igrejaplus.security.CustomUserDetailsService;
import com.igrejaplus.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // DESABILITA CSRF (JWT)
                .csrf(csrf -> csrf.disable())

                // SEM SESSÃO
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // AUTORIZAÇÃO
                .authorizeHttpRequests(auth -> auth

                        // AUTH
                        .requestMatchers("/api/auth/**").permitAll()

                        // CONFIGURAÇÕES
                        .requestMatchers(HttpMethod.GET,
                                "/api/config")
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT,
                                "/api/config")
                        .hasRole("ADMIN")

                        // EVENTO
                        .requestMatchers(HttpMethod.GET,
                                "/api/events/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/events/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/events/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/events/**")
                        .hasRole("ADMIN")

                        // LEITURA
                        .requestMatchers(HttpMethod.GET,
                                "/api/schedules/**",
                                "/api/comments/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MEMBER_READ_ONLY",
                                "MEMBER_COMMENT"
                        )
                        .requestMatchers(HttpMethod.POST,
                                "/api/schedules/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/schedules/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/schedules/**"
                        ).hasRole("ADMIN")
                        .requestMatchers( "/api/schedules/{scheduleId}/items/**")
                        .hasRole("ADMIN")

                        // COMENTÁRIOS
                        .requestMatchers(HttpMethod.POST, "/api/comments/**")
                        .hasAnyRole("ADMIN", "MEMBER_COMMENT")
                        .requestMatchers(HttpMethod.PATCH, "/api/comments/**")
                        .hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/comments/**")
                        .hasAnyRole("ADMIN")

                        // ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // MEMBRO LOGADO
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/members/me"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/members/me"
                        ).authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/members")
                        .hasRole("ADMIN")

                        // DEFAULT
                        .anyRequest().authenticated()
                )

                // JWT FILTER
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService customUserDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}