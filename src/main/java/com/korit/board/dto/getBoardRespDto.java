package com.korit.board.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class getBoardRespDto {
    private int boardId;
    private String boardTitle;
    private int boardCategoryId;
    private String boardContent;
    private String email;
    private String nickname;
    private String createDate;
    private int boardHitsCount;
    private int boardLikeCount;
}
