package com.korit.board.config;

import com.korit.board.filter.JwtAuthenticationFilter;
import com.korit.board.security.PrincipalEntryPoint;
import com.korit.board.security.oauth2.Oauth2SuccessHandler;
import com.korit.board.service.PrincipalUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalEntryPoint principalEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final PrincipalUserDetailsService principalUserDetailsService;
    private final Oauth2SuccessHandler oauth2SuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable(); // csrf = SSR 할 때 사용하는 토큰?

        http.authorizeRequests() // authorizeHttpRequests 이거 쓰면 안됨
                .antMatchers("/board/content", "board/like/**")
                .authenticated()
                .antMatchers("/auth/**", "/board/**", "/boards/**")
                .permitAll()
                .anyRequest()
                .authenticated() // 인증을 거친다 securityContextHolder 안에 authentication이 있는지 없는지 검사
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // UsernamePasswordAuthenticationFilter -> securityContextHolder에 Authentication이 이 있는지 검사
                .exceptionHandling()
                .authenticationEntryPoint(principalEntryPoint)
                .and()
                .oauth2Login()
                .loginPage("http://localhost/auth/signin") // 소셜로그인을 하면 --> endpoint
                .successHandler(oauth2SuccessHandler)
                .userInfoEndpoint() //** yml의 Uri들이 동작하고, 정보를 들고 service에 loadUser메소드에 OAuth2UserRequest로 전달 // controller같은 역할
                .userService(principalUserDetailsService); // == authentication manager
    }

}
