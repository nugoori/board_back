package com.korit.board.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterBoardReqDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
