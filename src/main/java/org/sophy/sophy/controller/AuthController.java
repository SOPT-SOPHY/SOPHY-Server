package org.sophy.sophy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.request.DuplCheckDto;
import org.sophy.sophy.controller.dto.request.MemberLoginRequestDto;
import org.sophy.sophy.controller.dto.request.MemberRequestDto;
import org.sophy.sophy.controller.dto.response.MemberResponseDto;
import org.sophy.sophy.controller.dto.response.TokenDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.jwt.TokenProvider;
import org.sophy.sophy.service.AuthService;
import org.sophy.sophy.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "회원 관리", description = "Auth 관련 API docs")
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final EmailService emailService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원가입")
    public ApiResponseDto<MemberResponseDto> signup(
        @RequestBody @Valid MemberRequestDto memberRequestDto) {
        return ApiResponseDto.success(SuccessStatus.SIGNUP_SUCCESS,
            authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ApiResponseDto<TokenDto> login(
        @RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        return ApiResponseDto.success(SuccessStatus.LOGIN_SUCCESS,
            authService.login(memberLoginRequestDto));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    @SecurityRequirement(name = "JWT Auth")
    public ApiResponseDto<String> logout(@Parameter(hidden = true) HttpServletRequest request) {
        /**
         * HttpServletRequest나 HttpServletResponse 객체가 Service 계층으로 넘어가는 것은 좋지 않다.
         * request, response는 컨트롤러 계층에서 사용되는 객체이며, Service 계층이 request와 response를 알 필요가 없다.
         */
        String accessToken = tokenProvider.resolveAccessToken(request);
        return ApiResponseDto.success(SuccessStatus.LOGOUT_SUCCESS,
            authService.logout(accessToken));
    }

    @PostMapping("/reissue")
    @Operation(summary = "액세스 토큰 재발행")
    @SecurityRequirements({
        @SecurityRequirement(name = "JWT Auth"),
        @SecurityRequirement(name = "Refresh")
    })
    public ApiResponseDto<TokenDto> reissue(@Parameter(hidden = true) HttpServletRequest request) {
        //HttpServletRequest 객체가 filter를 거친후 DispatherServlet을 지나서 만들어지는 객체라 무조건 filter를 타게됨 (config 설정해도)
        String accessToken = tokenProvider.resolveAccessToken(request);
        String refreshToken = tokenProvider.resolveRefreshToken(request);
        return ApiResponseDto.success(SuccessStatus.REISSUE_SUCCESS,
            authService.reissue(accessToken, refreshToken));
    }

    @PostMapping("/dupl-check")
    @Operation(summary = "이메일 중복 체크")
    public ApiResponseDto<String> duplCheck(@RequestBody DuplCheckDto email) {
        return ApiResponseDto.success(SuccessStatus.CHECK_DUPL_EMAIL_SUCCESS,
            authService.duplCheck(email));
    }

    @PostMapping("/email-confirm")
    @Operation(summary = "이메일 인증")
    public ApiResponseDto<String> emailConfirm(
        @Parameter(example = "member@naver.com") @RequestParam String email) throws Exception {
        return ApiResponseDto.success(SuccessStatus.CREATE_AUTHCODE_SUCCESS,
            emailService.sendEmail(email));
    }

    @PostMapping("/withdrawal")
    @Operation(summary = "회원 탈퇴")
    @SecurityRequirement(name = "JWT Auth")
    public ApiResponseDto<String> withdrawal(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.WITHDRAWAL_SUCCESS,
            authService.withdrawal(user.getUsername()));
    }
}
