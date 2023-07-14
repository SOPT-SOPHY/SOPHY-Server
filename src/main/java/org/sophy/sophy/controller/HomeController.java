package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.domain.dto.HomeResponseDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.HomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/myhome/{memberId}")
    public ApiResponseDto<HomeResponseDto> getHome(@PathVariable("memberId") Long memberId) {
        return ApiResponseDto.success(SuccessStatus.GET_HOME_SUCCESS, homeService.getHome(memberId));
    }

    @GetMapping("/home")
    public ApiResponseDto<HomeResponseDto> getHome() {
        return ApiResponseDto.success(SuccessStatus.GET_GUEST_HOME_SUCCESS, homeService.getGuestHome());
    }
}