package com.korit.board.entity;

import com.korit.board.dto.BoardListRespDto;
import com.korit.board.dto.UpdateBoardReqDto;
import com.korit.board.dto.getBoardRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
    private int boardId;
    private String boardTitle;
    private int boardCategoryId;
    private String boardContent;
    private String email;
    private String nickname;
    private LocalDateTime createDate;
    private int boardHitsCount;
    private int boardLikeCount;

    public BoardListRespDto toBoardListRespDto() {
        return BoardListRespDto.builder()
                .boardId(boardId)
                .title(boardTitle)
                .nickname(nickname)
                .createDate(createDate.format(DateTimeFormatter.ISO_DATE)) // year. month. day
                .hitsCount(boardHitsCount)
                .likeCount(boardLikeCount)
                .build();
    }

    public getBoardRespDto toBoardDto() {
        return getBoardRespDto.builder()
                .boardTitle(boardTitle)
                .boardCategoryId(boardCategoryId)
                .boardContent(boardContent)
                .email(email)
                .nickname(nickname)
                .createDate(createDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)))
                .boardHitsCount(boardHitsCount)
                .boardLikeCount(boardLikeCount)
                .build();
    }
}
