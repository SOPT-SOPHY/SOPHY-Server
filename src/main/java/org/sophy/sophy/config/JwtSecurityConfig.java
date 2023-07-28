package org.sophy.sophy.config;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.jwt.JwtExceptionFilter;
import org.sophy.sophy.jwt.JwtFilter;
import org.sophy.sophy.jwt.TokenProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//직접 만든 TokenProvider와 JwtFilter를 SecurityConfig에 적용할 때 사용
@RequiredArgsConstructor
public class JwtSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;
    private final JwtExceptionFilter jwtExceptionFilter;

    //TokenProvider를 주입받아서 JwtFillter를 통해 Security 로직에 필터를 등록
    //HttpSecurity의 userpassword인증필터에 filter 추가
    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenProvider, redisTemplate);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter, JwtFilter.class);
    }

}
