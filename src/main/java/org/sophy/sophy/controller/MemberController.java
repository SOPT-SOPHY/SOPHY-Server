package org.sophy.sophy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.domain.dto.mypage.MyInfoDto;
import org.sophy.sophy.domain.dto.mypage.MyPageDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "마이 페이지", description = "마이 페이지 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/my-page")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "마이 페이지 조회")
    public ApiResponseDto<MyPageDto> getMyPage(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_MY_PAGE_SUCCESS,
            memberService.geyMyPage(user.getUsername()));
    }

    @GetMapping("/my-info")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "내 정보 조회")
    public ApiResponseDto<MyInfoDto> getInfo(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_MY_INFO_SUCCESS,
            memberService.getMyInfo(user.getUsername()));
    }

    @PostMapping("/my-info")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "추가 정보 입력")
    public ApiResponseDto<MemberAdditionalInfoDto> postAdditionalInfo(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @RequestBody @Valid MemberAdditionalInfoDto memberAdditionalInfoDto) {
        return ApiResponseDto.success(SuccessStatus.POST_ADDITIONALINFO_SUCCESS,
            memberService.postAdditionalInfo(user.getUsername(), memberAdditionalInfoDto));
    }

    @PatchMapping("/my-info")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "내 정보 수정")
    public ApiResponseDto<MyInfoDto> patchInfo(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @RequestBody @Valid MyInfoDto myInfoDto) {
        return ApiResponseDto.success(SuccessStatus.PATCH_MY_INFO_SUCCESS,
            memberService.patchMyInfo(user.getUsername(), myInfoDto));
    }

}
