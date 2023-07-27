package org.sophy.sophy.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.domain.dto.SophyStoryDto;
import org.sophy.sophy.domain.dto.SophyStoryRequestDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.SophyStoryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sophy-story")
@RequiredArgsConstructor
@Tag(name = "소피 스토리", description = "소피 스토리 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
public class SophyStoryController {
    private final SophyStoryService sophyStoryService;

    @GetMapping //소피스토리 연, 월로 조회
    public ApiResponseDto<List<SophyStoryDto>> geyMySophyStory(@Parameter(hidden = true) @AuthenticationPrincipal User user, @Parameter @RequestBody SophyStoryRequestDto sophyStoryRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_SOPHY_STORY_SUCCESS, sophyStoryService.getMySophyStory(user.getUsername(), sophyStoryRequestDto));
    }

    @GetMapping("/all") //소피스토리 모두 조회
    public ApiResponseDto<List<SophyStoryDto>> geyMySophyStory(@Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_SOPHY_STORY_SUCCESS, sophyStoryService.getMySophyStory(user.getUsername()));
    }
}
