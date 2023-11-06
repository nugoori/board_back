package com.korit.board.service;
import com.korit.board.security.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.korit.board.repository.UserMapper;
import com.korit.board.entity.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PrincipalUserDetailsService implements UserDetailsService, OAuth2UserService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.findUserByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("usernameNotFound"); // 사용 안하는 중 - 여기서 던진 오류가 AuthService에서 catch할때 받는 오류를 printStackTrace()로 찍을 때 나옴
        }

        return new PrincipalUser(user);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        System.out.println("token " + userRequest.getAccessToken());
//        System.out.println("클라이언트 정보 " + userRequest.getClientRegistration());
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

//        System.out.println("oAuth2User " + oAuth2User.getAttributes());
        Map<String, Object> attribute = oAuth2User.getAttributes();
        Map<String, Object> response = (Map<String, Object>) attribute.get("response");
        String provider = userRequest.getClientRegistration().getClientName();

        response.put("provider", provider);
        
        return new DefaultOAuth2User(new ArrayList<>(), response, "id");
        // 정상 리턴(== authentication객체로 변환) --> successHandler의 onAuthenticationSuccess에 authentication객체로 전달
    }
}

