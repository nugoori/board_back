package com.korit.board.service;
import com.korit.board.security.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.korit.board.repository.UserMapper;
import com.korit.board.entity.User;

@Service
@RequiredArgsConstructor
public class PrincipalUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.findUserByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("usernameNotFound"); // 사용 안하는 중 - 여기서 던진 오류가 AuthService에서 catch할때 받는 오류를 printStackTrace()로 찍을 때 나옴
        }

        return new PrincipalUser(user);
    }
}
