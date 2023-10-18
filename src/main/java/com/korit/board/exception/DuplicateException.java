package com.korit.board.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class DuplicateException extends RuntimeException{
    private final Map<String, String> errorMap;

    public DuplicateException(Map<String, String> errorMap) {
        super("중복 검사 오류"); // super() 명령어로 부모의 기본 생성자를 부름
        this.errorMap = errorMap;
    }
}
