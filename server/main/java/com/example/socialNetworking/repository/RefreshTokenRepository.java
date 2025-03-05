package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.RefreshToken;
import com.example.socialNetworking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
}
