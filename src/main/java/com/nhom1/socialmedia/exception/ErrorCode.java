package com.nhom1.socialmedia.exception;

public enum ErrorCode {
    UNCATEGORYZED_EXCEPTION(9999, "Uncategorized error"),
    USER_EXISTED(1003, "User existed"),
    USERNAME_INVALID(1004, "Username must be at least 3 characters"),
    PASSWORD_INVALID(1005, "Password must be at least 6 characters"),
    USER_NOT_EXIST(1006, "Password wrong"),
    USERID_NOT_FOUND( 1007,"Id User not found"),
    POSTID_NOT_FOUND( 1008,"Id User not found");

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
