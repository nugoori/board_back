package com.korit.board.entity;

import com.korit.board.dto.PrincipalRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor // Mapper에서 xml파일을 class파일로 컴파일 할 때 사용될 수도 있음
@AllArgsConstructor
@Data
public class User {
    private int userId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private int enabled; // 1(인증O) or 0(인증X, 기본값)

    public PrincipalRespDto toPrincipalDto() {
        return PrincipalRespDto.builder()
                .userId(userId)
                .email(email)
                .name(name)
                .nickname(nickname)
                .enabled(enabled > 0)
                .build();
    }
}

