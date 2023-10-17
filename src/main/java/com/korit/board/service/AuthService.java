package com.korit.board.service;

import com.korit.board.dto.SignupReqDto;
import com.korit.board.entity.User;
import com.korit.board.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 사용 하는 부품마다 Autowired를 달아 주기 힘들기 때문에 사용
public class AuthService {

    private final UserMapper userMapper; // 생성자로 생성할 때 초기화
    private final BCryptPasswordEncoder passwordEncoder; // 메소드를 DI할 때 이름이 달라도 되긴 하지만 여러 개일 경우를 대비해 이름을 꼭 맞춰 주기?

    public boolean signUp(SignupReqDto signupReqDto) {
        User user = signupReqDto.toUserEntity(passwordEncoder);
        // 유효성 검사
        return userMapper.saveUser(user) > 0;
    }
}
