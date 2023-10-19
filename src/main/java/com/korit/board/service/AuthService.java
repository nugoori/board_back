package com.korit.board.service;

import com.korit.board.dto.SigninReqDto;
import com.korit.board.dto.SignupReqDto;
import com.korit.board.entity.User;
import com.korit.board.exception.DuplicateException;
import com.korit.board.jwt.JwtProvider;
import com.korit.board.repository.UserMapper;
import com.korit.board.security.PrincipalProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor // 사용 하는 부품마다 Autowired를 달아 주기 힘들기 때문에 사용
public class AuthService {

    private final UserMapper userMapper; // 생성자로 생성할 때 초기화
    private final BCryptPasswordEncoder passwordEncoder; // 메소드를 DI할 때 이름이 달라도 되긴 하지만 여러 개일 경우를 대비해 이름을 꼭 맞춰 주기?
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PrincipalProvider principalProvider;
    private final JwtProvider jwtProvider;

    public boolean signUp(SignupReqDto signupReqDto) {
        User user = signupReqDto.toUserEntity(passwordEncoder);

        int errorCode = userMapper.duplicateUser(user);
        if(errorCode > 0) {
            responseDuplicateError(errorCode);
        }
        // 유효성 검사

        return userMapper.saveUser(user) > 0;
    }

    private void responseDuplicateError(int errorCode) {
        Map<String, String> errorMap = new HashMap<>();
        switch(errorCode) {
            case 1:
                errorMap.put("email", "이미 사용중인 이메일입니다.");
                break;
            case 2:
                errorMap.put("nickname", "이미 사용중인 닉네임입니다.");
                break;
            case 3:
                errorMap.put("email", "이미 사용중인 이메일입니다.");
                errorMap.put("nickname", "이미 사용중인 닉네임입니다.");
                break;
        }
        throw new DuplicateException(errorMap);
    }

    public String signin(SigninReqDto signinReqDto) {
        // 1
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signinReqDto.getEmail(), signinReqDto.getPassword());

        // 2 Authentication이 호출? 되면 -> loaduserbyusername
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // PrincipalUser - userDetails 에서 검사 할 때 비밀번호 검사보다 isEnabled가 먼저 검사해서 아래 방법으로 순서를 다르게 
        Authentication authentication = principalProvider.authenticate(authenticationToken);

        String jwtToken = jwtProvider.generateJwtToken(authentication);

        return jwtToken;
    }

    public boolean authenticate(String token) {
        Claims claims = jwtProvider.getClaims(token);
        if(claims == null) {
            throw new JwtException("인증 토큰 유효성 검사 실패");
        }
        return Boolean.parseBoolean(claims.get("enabled").toString());
    }
}
