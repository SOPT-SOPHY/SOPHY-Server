package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.config.jwt.JwtService;
import org.sophy.sophy.controller.request.dto.UserLoginRequestDto;
import org.sophy.sophy.controller.request.dto.UserRequestDto;
import org.sophy.sophy.controller.response.dto.UserLoginResponseDto;
import org.sophy.sophy.controller.response.dto.UserResponseDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<UserLoginResponseDto> login(@RequestBody @Valid final UserLoginRequestDto request) {
        final Long userId = userService.login(request);
        final String token = jwtService.issuedToken(String.valueOf(userId));
        return ApiResponseDto.success(SuccessStatus.LOGIN_SUCCESS, UserLoginResponseDto.of(userId, token));
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<UserResponseDto> create(@RequestBody @Valid final UserRequestDto request) {
        return ApiResponseDto.success(SuccessStatus.SIGNUP_SUCCESS, userService.create(request));
    }
}
