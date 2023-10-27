package com.korit.board.dto;

import com.korit.board.entity.Board;
import com.korit.board.entity.UpdateBoard;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateBoardReqDto {
    private String title;
    private String content;
    private String email;

    public Board toBoardEntity(String email) {
        return Board.builder()
                .boardTitle(title)
                .boardContent(content)
                .email(email)
                .build();
    }
}
