package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.config.jwt.JwtService;
import org.sophy.sophy.controller.dto.request.MemberLoginRequestDto;
import org.sophy.sophy.controller.dto.request.MemberRequestDto;
import org.sophy.sophy.controller.dto.response.MemberLoginResponseDto;
import org.sophy.sophy.controller.dto.response.MemberResponseDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MemberLoginResponseDto> login(@RequestBody @Valid final MemberLoginRequestDto request) {
        final Long memberId = memberService.login(request);
        final String token = jwtService.issuedToken(String.valueOf(memberId));
        return ApiResponseDto.success(SuccessStatus.LOGIN_SUCCESS, MemberLoginResponseDto.of(memberId, token));
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MemberResponseDto> create(@RequestBody @Valid final MemberRequestDto request) {
        return ApiResponseDto.success(SuccessStatus.SIGNUP_SUCCESS, memberService.create(request));
    }
}
