package com.igrejaplus.security;

import com.igrejaplus.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey accessKey;
    private final SecretKey refreshKey;

    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtUtil(
            @Value("${jwt.secret}") String accessSecret,
            @Value("${jwt.refresh-secret}") String refreshSecret,
            @Value("${jwt.expiration}") long accessExpiration,
            @Value("${jwt.refresh-expiration}") long refreshExpiration
    ) {
        this.accessKey = Keys.hmacShaKeyFor(accessSecret.getBytes());
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    /* ================= ACCESS TOKEN ================= */

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        String email = extractEmailFromAccess(token);
        return email.equals(userDetails.getUsername()) && !isExpired(token, accessKey);
    }

    public String extractEmailFromAccess(String token) {
        return parse(token, accessKey).getSubject();
    }

    /* ================= REFRESH TOKEN ================= */

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmailFromRefresh(String token) {
        return parse(token, refreshKey).getSubject();
    }

    /* ================= HELPERS ================= */

    private boolean isExpired(String token, SecretKey key) {
        return parse(token, key).getExpiration().before(new Date());
    }

    private Claims parse(String token, SecretKey key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

