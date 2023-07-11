package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.domain.dto.MyPageBooktalkDto;
import org.sophy.sophy.domain.dto.MyPageDto;
import org.sophy.sophy.domain.dto.MyInfoDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/my-page/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MyPageDto> getMyPage(@PathVariable("memberId") Long memberId) {
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS, memberService.getMyPage(memberId));
    }

    @GetMapping("/my-info/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MyInfoDto> getInfo(@PathVariable("memberId") Long memberId) {
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS, memberService.getMyInfo(memberId));
    }

    @PostMapping("/my-info/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MemberAdditionalInfoDto> postAdditionalInfo(@PathVariable("memberId") Long memberId, @RequestBody MemberAdditionalInfoDto memberAdditionalInfoDto) {
        return ApiResponseDto.success(SuccessStatus.POST_ADDITIONALINFO_SUCCESS, memberService.postAdditionalInfo(memberId, memberAdditionalInfoDto));
    }

    @PatchMapping("/my-info/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MyInfoDto> patchInfo(@PathVariable("memberId") Long memberId, @RequestBody MyInfoDto myInfoDto) {
        return ApiResponseDto.success(SuccessStatus.PATCH_MYINFO_SUCCESS, memberService.patchMyInfo(memberId, myInfoDto));
    }

    @GetMapping("/my-booktalks/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<MyPageBooktalkDto>> getMyBooktalks(@PathVariable("memberId") Long memberId) {
        return ApiResponseDto.success(SuccessStatus.GET_MY_BOOKTALKS_SUCCESS, memberService.getBooktalksByMemberId(memberId));
    }

    @GetMapping("/author-booktalks/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<MyPageBooktalkDto>> getAuthorBooktalks(@PathVariable("memberId") Long memberId) {
        return ApiResponseDto.success(SuccessStatus.GET_AUTHOR_BOOKTALKS_SUCCESS, memberService.getAuthorByMemberId(memberId));
    }
}
