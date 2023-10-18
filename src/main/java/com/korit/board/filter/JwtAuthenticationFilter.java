package com.korit.board.filter;

import com.korit.board.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String bearerToken = httpServletRequest.getHeader("Authorization");
        String token = jwtProvider.getToken(bearerToken); // header에 Authorization에 든 token에 "Bearer "를 제거하고 가져옴
        Authentication authentication = jwtProvider.getAuthentication(token); //

        if(authentication != null) {
            // Security에는 - Context객체 존재 - Authentication: 뭐라도 들어있으면 인증된 것으로 봄
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
