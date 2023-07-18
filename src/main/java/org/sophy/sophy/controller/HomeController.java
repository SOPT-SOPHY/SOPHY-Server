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

    @GetMapping("/myhome/{memberId}") //회원 홈 조회 (작가와 주민 구분은 서비스 단 내에서)
    public ApiResponseDto<HomeResponseDto> getHome(@PathVariable("memberId") Long memberId) {
        return ApiResponseDto.success(SuccessStatus.GET_HOME_SUCCESS, homeService.getHome(memberId));
    }

    @GetMapping("/home") // 비회원 홈 조회
    public ApiResponseDto<HomeResponseDto> getHome() {
        return ApiResponseDto.success(SuccessStatus.GET_GUEST_HOME_SUCCESS, homeService.getGuestHome());
    }
}
