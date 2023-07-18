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
import org.sophy.sophy.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<MemberResponseDto> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        return ApiResponseDto.success(SuccessStatus.SIGNUP_SUCCESS, authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ApiResponseDto<TokenDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        return ApiResponseDto.success(SuccessStatus.LOGIN_SUCCESS, authService.login(memberLoginRequestDto));
    }

    @PostMapping("/reissue")
    public ApiResponseDto<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ApiResponseDto.success(SuccessStatus.REISSUE_SUCCESS, authService.reissue(tokenRequestDto));
    }

    @PostMapping("/dupl-check")
    public ApiResponseDto<String> duplCheck(@RequestBody DuplCheckDto email) {
        return ApiResponseDto.success(SuccessStatus.CHECK_DUPL_EMAIL_SUCCESS, authService.duplCheck(email));
    }
}
