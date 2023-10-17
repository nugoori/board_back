package com.korit.board.dto;

import com.korit.board.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignupReqDto {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String nickname;

    // setter로 serivce에서 바꾸는 것보다 dto에서 바꿔주는게 service부분이 깔끔해진다(리펙터링). setter로 바꾸는 것과 builder로 만드는게 똑같다
    public User toUserEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .build();
    }
}
