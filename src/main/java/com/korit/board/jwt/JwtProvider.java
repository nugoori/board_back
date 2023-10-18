package com.korit.board.jwt;

import com.korit.board.entity.User;
import com.korit.board.repository.UserMapper;
import com.korit.board.security.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
    private final UserMapper userMapper;

    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Autowired UserMapper userMapper) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.userMapper = userMapper;
    }

    public String generateJwtToken(Authentication authentication) {
        String email = authentication.getName();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        Date date = new Date(new Date().getTime() + 1000 * 60 * 60 * 24);

        return Jwts.builder().
                setSubject("AccessToken")
                .setExpiration(date)
                .claim("email", email)
                .claim("isEnabled", principalUser.isEnabled())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // secret key로 jwtToken 분해 -> user 정보
    public Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    public String getToken(String bearerToken) {
        if(!StringUtils.hasText(bearerToken)) {
            return null;
        }
        return bearerToken.substring("Bearer ".length());
    }

    public Authentication getAuthentication(String token) {
        // 이름 노출을 막기위해 Jwt token이 아닌 DB에서 찾아옴
        Claims claims = getClaims(token);
        if(claims == null) {
            return null;
        }

        User user = userMapper.findUserByEmail(claims.get("email").toString());
        if(user == null) {
            return null;
        }
        PrincipalUser principalUser = new PrincipalUser(user);
        return new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
    }
}
