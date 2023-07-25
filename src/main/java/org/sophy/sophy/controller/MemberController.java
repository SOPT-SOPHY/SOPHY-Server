package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.domain.dto.mypage.MyPageBooktalkDto;
import org.sophy.sophy.domain.dto.mypage.MyPageDto;
import org.sophy.sophy.domain.dto.mypage.MyInfoDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/my-page") // 마이페이지 조회
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MyPageDto> getMyPage(@AuthenticationPrincipal User user) {
        Long memberId = memberRepository.getMemberByEmail(user.getUsername()).getId();
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS, memberService.getMyPage(memberId));
    }

    @GetMapping("/my-info") //내 정보 조회
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MyInfoDto> getInfo(@AuthenticationPrincipal User user) {
        Long memberId = memberRepository.getMemberByEmail(user.getUsername()).getId();
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS, memberService.getMyInfo(memberId));
    }

    @PostMapping("/my-info") //추가 정보 입력
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MemberAdditionalInfoDto> postAdditionalInfo(@AuthenticationPrincipal User user, @RequestBody @Valid MemberAdditionalInfoDto memberAdditionalInfoDto) {
        Long memberId = memberRepository.getMemberByEmail(user.getUsername()).getId();
        return ApiResponseDto.success(SuccessStatus.POST_ADDITIONALINFO_SUCCESS, memberService.postAdditionalInfo(memberId, memberAdditionalInfoDto));
    }

    @PatchMapping("/my-info") //내 정보 업데이트
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<MyInfoDto> patchInfo(@AuthenticationPrincipal User user, @RequestBody @Valid MyInfoDto myInfoDto) {
        Long memberId = memberRepository.getMemberByEmail(user.getUsername()).getId();
        return ApiResponseDto.success(SuccessStatus.PATCH_MYINFO_SUCCESS, memberService.patchMyInfo(memberId, myInfoDto));
    }

    @GetMapping("/my-booktalks") //예정된 북토크 조회 (신청)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<MyPageBooktalkDto>> getMyBooktalks(@AuthenticationPrincipal User user) {
        Long memberId = memberRepository.getMemberByEmail(user.getUsername()).getId();
        return ApiResponseDto.success(SuccessStatus.GET_MY_BOOKTALKS_SUCCESS, memberService.getBooktalksByMemberId(memberId));
    }

    @GetMapping("/author-booktalks") //개설한 북토크 조회
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<MyPageBooktalkDto>> getAuthorBooktalks(@AuthenticationPrincipal User user) {
        Long memberId = memberRepository.getMemberByEmail(user.getUsername()).getId();
        return ApiResponseDto.success(SuccessStatus.GET_AUTHOR_BOOKTALKS_SUCCESS, memberService.getAuthorBooktalksByMemberId(memberId));
    }
}
