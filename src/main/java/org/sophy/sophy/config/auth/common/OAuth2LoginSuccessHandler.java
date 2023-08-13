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
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.infrastructure.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final RedisTemplate redisTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
        if(customOAuth2User.getAuthority() == Authority.GUEST) {
            response.sendRedirect("/auth/signup"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트 (추가 컨트롤러를 만들고 거기서 post 해야지 User로 바뀌게 해야겠다)

            //아래의 GUEST를 USER로 바꿔주는 로직은 회원가입 승인되면서 처리되게 변경
            Member findUser = memberRepository.getMemberByEmail(customOAuth2User.getEmail());
            findUser.authorizeUser();
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
