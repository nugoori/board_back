package com.korit.board.exception;


public class MismatchPasswordException extends RuntimeException{

    public MismatchPasswordException() {
        super("비밀 번호가 서로 일치하지 않습니다.");
    }
}
