package com.example.socialNetworking.service;

import com.example.socialNetworking.model.RefreshToken;
import com.example.socialNetworking.model.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String email);
    boolean isValid(String token);
    void deleteByToken(String email);

    User findUserByRefreshToken(String refreshToken);
}
