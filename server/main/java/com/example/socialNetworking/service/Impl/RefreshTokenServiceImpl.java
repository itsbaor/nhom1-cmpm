package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.exception.RefreshTokenException;
import com.example.socialNetworking.model.RefreshToken;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.RefreshTokenRepository;
import com.example.socialNetworking.service.RefreshTokenService;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Override
    public RefreshToken createRefreshToken(String email) {
        User user = userService.getUserByEmail(email);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(String.valueOf(UUID.randomUUID()));
        refreshToken.setExpires(Instant.now().plus(7, ChronoUnit.DAYS));

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public boolean isValid(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(t-> t.getExpires().isAfter(Instant.now()))
                .get();
    }

    @Transactional
    @Override
    public void deleteByToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }

    @Override
    public User findUserByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .map(t-> t.getUser()).orElseThrow(() -> new RefreshTokenException("RefreshToken not found"));
    }
}
