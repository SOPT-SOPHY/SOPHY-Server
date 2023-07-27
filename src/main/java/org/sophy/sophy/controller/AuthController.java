package org.sophy.sophy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.request.DuplCheckDto;
import org.sophy.sophy.controller.dto.request.MemberLoginRequestDto;
import org.sophy.sophy.controller.dto.request.MemberRequestDto;
import org.sophy.sophy.controller.dto.request.TokenRequestDto;
import org.sophy.sophy.controller.dto.response.MemberResponseDto;
import org.sophy.sophy.controller.dto.response.TokenDto;
import org.sophy.sophy.exception.SuccessStatus;
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
@Tag(name = "회원 관리", description = "Auth 관련 API docs")
public class AuthController {
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원가입")
    public ApiResponseDto<MemberResponseDto> signup(@Parameter @RequestBody @Valid MemberRequestDto memberRequestDto) {
        return ApiResponseDto.success(SuccessStatus.SIGNUP_SUCCESS, authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ApiResponseDto<TokenDto> login(@Parameter @RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        return ApiResponseDto.success(SuccessStatus.LOGIN_SUCCESS, authService.login(memberLoginRequestDto));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    public ApiResponseDto<String> logout(HttpServletRequest request) {
        /**
         * HttpServletRequest나 HttpServletResponse 객체가 Service 계층으로 넘어가는 것은 좋지 않다.
         * request, response는 컨트롤러 계층에서 사용되는 객체이며, Service 계층이 request와 response를 알 필요가 없다.
         */
        String accessToken = tokenProvider.resolveAccessToken(request);
        return ApiResponseDto.success(SuccessStatus.LOGOUT_SUCCESS, authService.logout(accessToken));
    }

    @PostMapping("/reissue")
    @Operation(summary = "액세스 토큰 재발행")
    public ApiResponseDto<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) { //추후 토큰 만료시간 설정하고 Refresh 토큰 헤더로 받게 변경 필요
        return ApiResponseDto.success(SuccessStatus.REISSUE_SUCCESS, authService.reissue(tokenRequestDto));
    }

    @PostMapping("/dupl-check")
    @Operation(summary = "이메일 중복 체크")
    public ApiResponseDto<String> duplCheck(@RequestBody DuplCheckDto email) {
        return ApiResponseDto.success(SuccessStatus.CHECK_DUPL_EMAIL_SUCCESS, authService.duplCheck(email));
    }

    @PostMapping("/withdrawal")
    @Operation(summary = "회원 탈퇴")
    @SecurityRequirement(name = "JWT Auth")
    public ApiResponseDto<String> withdrawal(@AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.WITHDRAWAL_SUCCESS, authService.withdrawal(user.getUsername()));
    }
}
