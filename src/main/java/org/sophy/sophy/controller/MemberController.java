package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.domain.dto.mypage.MyPageDto;
import org.sophy.sophy.domain.dto.mypage.MyInfoDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/my-page") // 마이페이지 조회
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MyPageDto> getMyPage(@AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS, memberService.getMyPage(user.getUsername()));
    }

    @GetMapping("/my-info") //내 정보 조회
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MyInfoDto> getInfo(@AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS, memberService.getMyInfo(user.getUsername()));
    }

    @PostMapping("/my-info") //추가 정보 입력
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MemberAdditionalInfoDto> postAdditionalInfo(@AuthenticationPrincipal User user, @RequestBody @Valid MemberAdditionalInfoDto memberAdditionalInfoDto) {
        return ApiResponseDto.success(SuccessStatus.POST_ADDITIONALINFO_SUCCESS, memberService.postAdditionalInfo(user.getUsername(), memberAdditionalInfoDto));
    }

    @PatchMapping("/my-info") //내 정보 업데이트
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MyInfoDto> patchInfo(@AuthenticationPrincipal User user, @RequestBody @Valid MyInfoDto myInfoDto) {
        return ApiResponseDto.success(SuccessStatus.PATCH_MYINFO_SUCCESS, memberService.patchMyInfo(user.getUsername(), myInfoDto));
    }

}
