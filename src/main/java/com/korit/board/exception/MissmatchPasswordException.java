package com.korit.board.exception;


public class MissmatchPasswordException extends RuntimeException{

    public MissmatchPasswordException() {
        super("비밀 번호가 서로 일치하지 않습니다.");
    }
}
