package com.korit.board.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateBoard {
    private String boardTitle;
    private String boardContent;
    private String email;
}
