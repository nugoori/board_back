package com.korit.board.security;

import com.korit.board.service.PrincipalUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrincipalProvider implements AuthenticationProvider {

    private final PrincipalUserDetailsService principalUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    // managerBuilder대신 직접 authenticate 구현 해보기
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        // loadUserByUsername의 usernameNotFound 오류가 AuthenticationException으로 던져지고 authenticate가 호출된 AuthService에서 try catch로 오류를 잡아 줘야함
        UserDetails principalUser = principalUserDetailsService.loadUserByUsername(email);

        // 복호화는 안되지만 암호화된 비밀번호를 암호화 되기 전 비밀번호와 비교 해서 boolean값으로 리턴해 줌
        if(!passwordEncoder.matches(password, principalUser.getPassword())) {
            throw new BadCredentialsException("BadCredential");
        }

        // 필요한 예외들 추가로 작성

        return new UsernamePasswordAuthenticationToken(principalUser, password, principalUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
