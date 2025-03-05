package com.example.socialNetworking.exception;

public class RefreshTokenException extends RuntimeException {
    public RefreshTokenException(String refreshTokenNotFound) {
        super(refreshTokenNotFound);
    }
}
