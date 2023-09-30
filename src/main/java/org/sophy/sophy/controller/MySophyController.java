package org.sophy.sophy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.domain.dto.SophyStoryDto;
import org.sophy.sophy.domain.dto.SophyStoryRequestDto;
import org.sophy.sophy.domain.dto.mypage.MyPageBooktalkDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.MySophyService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sophy-story")
// TODO 나의 소피로 uri 변경 필요
@RequiredArgsConstructor
@Tag(name = "나의 소피", description = "나의 소피 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
public class MySophyController {

    private final MySophyService mySophyService;

    @GetMapping //소피스토리 연, 월로 조회
    public ApiResponseDto<List<SophyStoryDto>> geyMySophyStory(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @RequestBody @Valid SophyStoryRequestDto sophyStoryRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_SOPHY_STORY_SUCCESS,
            mySophyService.getMySophyStory(user.getUsername(), sophyStoryRequestDto));
    }

    @GetMapping("/all") //소피스토리 모두 조회
    public ApiResponseDto<List<SophyStoryDto>> geyMySophyStory(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_SOPHY_STORY_SUCCESS,
            mySophyService.getMySophyStory(user.getUsername()));
    }

    @GetMapping("/booktalks")
    @Operation(summary = "예정된 북토크 조회")
    public ApiResponseDto<List<MyPageBooktalkDto>> getMyBooktalks(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_MY_BOOKTALKS_SUCCESS,
            mySophyService.getBooktalksByMember(user.getUsername()));
    }
}
