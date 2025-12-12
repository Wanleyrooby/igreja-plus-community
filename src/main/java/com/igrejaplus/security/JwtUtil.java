package com.igrejaplus.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final SecretKey refreshSecretKey;

    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.refresh-secret}") String refreshSecret,
            @Value("${jwt.expiration}") long accessExpiration,
            @Value("${jwt.refresh-expiration}") long refreshExpiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    // ========================================
    // ACCESS TOKEN
    // ========================================

    public String generateAccessToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isAccessTokenValid(String token) {
        return validateToken(token, secretKey);
    }

    public String extractEmail(String token) {
        return extractClaims(token, secretKey).getSubject();
    }

    public String extractRole(String token) {
        return extractClaims(token, secretKey).get("role", String.class);
    }

    // ========================================
    // REFRESH TOKEN
    // ========================================

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(refreshSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isRefreshTokenValid(String token) {
        return validateToken(token, refreshSecretKey);
    }

    public String extractEmailFromRefresh(String token) {
        return extractClaims(token, refreshSecretKey).getSubject();
    }

    // ========================================
    // MÉTODO USADO PELO FILTER
    // ========================================

    public boolean isTokenValid(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && isAccessTokenValid(token));
    }

    // ========================================
    // HELPERS
    // ========================================

    private boolean validateToken(String token, SecretKey key) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims extractClaims(String token, SecretKey key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
