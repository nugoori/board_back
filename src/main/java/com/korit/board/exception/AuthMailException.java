package com.korit.board.exception;

import lombok.Getter;

import java.util.Map;

public class AuthMailException extends RuntimeException{

    public AuthMailException(String message) {
        super(message);
    }
}
