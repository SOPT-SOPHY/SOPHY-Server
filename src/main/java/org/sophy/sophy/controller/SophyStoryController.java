package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.domain.dto.SophyStoryDto;
import org.sophy.sophy.domain.dto.SophyStoryRequestDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.SophyStoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sophy-story")
@RequiredArgsConstructor
public class SophyStoryController {
    private final SophyStoryService sophyStoryService;

    @GetMapping("/{memberId}") //소피스토리 연, 월로 조회
    public ApiResponseDto<List<SophyStoryDto>> geyMySophyStory(@PathVariable(name = "memberId") Long memberId, @RequestBody SophyStoryRequestDto sophyStoryRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_SOPHY_STORY_SUCCESS, sophyStoryService.getMySophyStory(memberId, sophyStoryRequestDto));
    }

    @GetMapping("/{memberId}/all") //소피스토리 모두 조회
    public ApiResponseDto<List<SophyStoryDto>> geyMySophyStory(@PathVariable(name = "memberId") Long memberId) {
        return ApiResponseDto.success(SuccessStatus.GET_SOPHY_STORY_SUCCESS, sophyStoryService.getMySophyStory(memberId));
    }
}
