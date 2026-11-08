package com.fastfix.exception;

public class UserUpdateConflictException extends RuntimeException {
    public UserUpdateConflictException(String message) {
        super(message);
    }
}
