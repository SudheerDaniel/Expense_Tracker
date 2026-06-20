package com.example.springbootmvcexample.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.springbootmvcexample.model.RefreshToken;
import com.example.springbootmvcexample.repository.RefreshTokenRepository;
import com.example.springbootmvcexample.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    // creates and stores a new refresh token for a user, removing any previous one first
    // (so a user only ever has one valid refresh token at a time, simplest model)
    public String createRefreshToken(Long userId, String email) {
        refreshTokenRepository.deleteByUserId(userId);

        String tokenString = jwtUtil.generateRefreshToken(email);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(tokenString);
        refreshToken.setUserId(userId);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));

        refreshTokenRepository.save(refreshToken);
        return tokenString;
    }

    // validates that a refresh token exists in the database and hasn't expired
    public Optional<RefreshToken> validateRefreshToken(String token) {
        Optional<RefreshToken> stored = refreshTokenRepository.findByToken(token);
        if (stored.isEmpty()) {
            return Optional.empty();
        }
        if (stored.get().getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(stored.get());
            return Optional.empty();
        }
        return stored;
    }

    // deletes a user's refresh token - used on logout
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}
