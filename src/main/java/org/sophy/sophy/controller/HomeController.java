package org.sophy.sophy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.domain.dto.HomeResponseDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.service.HomeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Tag(name = "홈", description = "홈페이지 관련 API docs")
public class HomeController {
    private final HomeService homeService;
    private final MemberRepository memberRepository;

    @GetMapping("/myhome") //작가와 주민 구분은 서비스 단 내에서
    @Operation(summary = "회원 홈 조회")
    public ApiResponseDto<HomeResponseDto> getHome(@AuthenticationPrincipal User user) {
        Long memberId = memberRepository.getMemberByEmail(user.getUsername()).getId();
        return ApiResponseDto.success(SuccessStatus.GET_HOME_SUCCESS, homeService.getHome(memberId));
    }

    @GetMapping("/home")
    @Operation(summary = "비회원 홈 조회")
    public ApiResponseDto<HomeResponseDto> getHome() {
        return ApiResponseDto.success(SuccessStatus.GET_GUEST_HOME_SUCCESS, homeService.getGuestHome());
    }
}
