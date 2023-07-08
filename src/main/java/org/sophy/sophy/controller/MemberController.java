package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.domain.MyPageDto;
import org.sophy.sophy.domain.dto.MyInfoDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/my-page/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MyPageDto> getMyPage(@PathVariable("memberId") long memberId) {
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS, memberService.getMyPage(memberId));
    }

    @GetMapping("/my-info/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MyInfoDto> getInfo(@PathVariable("memberId") long memberId) {
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS, memberService.getMyInfo(memberId));
    }
}
