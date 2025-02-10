package com.FrumoStore.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message, String nickname) {
        super(String.format(message, nickname));
    }
}