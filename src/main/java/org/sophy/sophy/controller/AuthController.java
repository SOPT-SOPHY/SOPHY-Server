package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.request.DuplCheckDto;
import org.sophy.sophy.controller.dto.request.MemberLoginRequestDto;
import org.sophy.sophy.controller.dto.request.MemberRequestDto;
import org.sophy.sophy.controller.dto.request.TokenRequestDto;
import org.sophy.sophy.controller.dto.response.MemberResponseDto;
import org.sophy.sophy.controller.dto.response.TokenDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.jwt.TokenProvider;
import org.sophy.sophy.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @PostMapping("/signup") //회원가입
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<MemberResponseDto> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        return ApiResponseDto.success(SuccessStatus.SIGNUP_SUCCESS, authService.signup(memberRequestDto));
    }

    @PostMapping("/login") //로그인
    public ApiResponseDto<TokenDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        return ApiResponseDto.success(SuccessStatus.LOGIN_SUCCESS, authService.login(memberLoginRequestDto));
    }

    @PostMapping("/logout") //로그아웃
    public ApiResponseDto<String> logout(HttpServletRequest request) {
        /**
         * HttpServletRequest나 HttpServletResponse 객체가 Service 계층으로 넘어가는 것은 좋지 않다.
         * request, response는 컨트롤러 계층에서 사용되는 객체이며, Service 계층이 request와 response를 알 필요가 없다.
         */
        String accessToken = tokenProvider.resolveAccessToken(request);
        return ApiResponseDto.success(SuccessStatus.LOGOUT_SUCCESS, authService.logout(accessToken));
    }

    @PostMapping("/reissue") //액세스 토큰 재발행
    public ApiResponseDto<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) { //추후 토큰 만료시간 설정하고 Refresh 토큰 헤더로 받게 변경 필요
        return ApiResponseDto.success(SuccessStatus.REISSUE_SUCCESS, authService.reissue(tokenRequestDto));
    }

    @PostMapping("/dupl-check") //이메일 중복 체크
    public ApiResponseDto<String> duplCheck(@RequestBody DuplCheckDto email) {
        return ApiResponseDto.success(SuccessStatus.CHECK_DUPL_EMAIL_SUCCESS, authService.duplCheck(email));
    }

    @PostMapping("/withdrawal")
    public ApiResponseDto<String> withdrawal(@AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.WITHDRAWAL_SUCCESS, authService.withdrawal(user.getUsername()));
    }
}
