package com.nhom1.socialmedia.exception;

public enum ErrorCode {
    UNCATEGORYZED_EXCEPTION(9999, "Uncategorized error"),
    USER_EXISTED(1003, "User existed"),
    USERNAME_INVALID(1004, "Username must be at least 3 characters"),
    PASSWORD_INVALID(1005, "Password must be at least 6 characters"),
    USER_NOT_EXIST(1006, "Password wrong");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}
