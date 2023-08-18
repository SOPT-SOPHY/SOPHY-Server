package org.sophy.sophy.config.auth.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.config.auth.CustomOAuth2User;
import org.sophy.sophy.controller.dto.response.TokenDto;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.jwt.TokenProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 현재 로직
     * 1. 로그인 성공 but 처음 온 회원
     * 2. access token주고 회원 가입 페이지로 리다이렉트
     * 3. post를 보내면 token의 이메일로 Member 찾기
     * 4. Request Dto의 값들로 Member 값 변경 (더티체킹으로 트랜잭션이 끝날 때 자동으로 업뎃 쿼리 나감)
     * 5. 소셜 로그인 회원가입 끝~~
     */
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
        if(customOAuth2User.getAuthority() == Authority.GUEST) {
            String accessToken = tokenProvider.generateAccessToken(authentication);
            response.addHeader("Authorization", "Bearer " + accessToken);
            response.sendRedirect("/test"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트 (추가 컨트롤러를 만들고 거기서 post 해야지 User로 바뀜)
        } else {
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
            redisTemplate.opsForValue().set("RT:" + authentication.getName(),
                tokenDto.getRefreshToken(),
                tokenProvider.getRefreshTokenExpireTime(),
                TimeUnit.MILLISECONDS);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(mapper.writeValueAsString(
                ApiResponseDto.success(SuccessStatus.LOGIN_SUCCESS,
                    tokenDto)
            ));
            // 로그인에 성공한 경우 access, refresh 토큰 생성
        }
    }
}
