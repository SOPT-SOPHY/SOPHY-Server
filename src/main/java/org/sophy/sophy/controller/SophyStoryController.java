package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.domain.dto.SophyStoryDto;
import org.sophy.sophy.domain.dto.SophyStoryRequestDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.service.SophyStoryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sophy-story")
@RequiredArgsConstructor
public class SophyStoryController {
    private final SophyStoryService sophyStoryService;
    private final MemberRepository memberRepository;

    @GetMapping //소피스토리 연, 월로 조회
    public ApiResponseDto<List<SophyStoryDto>> geyMySophyStory(@AuthenticationPrincipal User user, @RequestBody SophyStoryRequestDto sophyStoryRequestDto) {
        Long memberId = memberRepository.getMemberByEmail(user.getUsername()).getId();
        return ApiResponseDto.success(SuccessStatus.GET_SOPHY_STORY_SUCCESS, sophyStoryService.getMySophyStory(memberId, sophyStoryRequestDto));
    }

    @GetMapping("/all") //소피스토리 모두 조회
    public ApiResponseDto<List<SophyStoryDto>> geyMySophyStory(@AuthenticationPrincipal User user) {
        Long memberId = memberRepository.getMemberByEmail(user.getUsername()).getId();
        return ApiResponseDto.success(SuccessStatus.GET_SOPHY_STORY_SUCCESS, sophyStoryService.getMySophyStory(memberId));
    }
}
