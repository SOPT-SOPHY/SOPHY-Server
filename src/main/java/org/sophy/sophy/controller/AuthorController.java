package org.sophy.sophy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.domain.CompletedBooktalk;
import org.sophy.sophy.domain.dto.booktalk.BooktalkUpdateDto;
import org.sophy.sophy.domain.dto.booktalk.request.BooktalkRequestDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkCreateResponseDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkDeleteResponseDto;
import org.sophy.sophy.domain.dto.mypage.MyPageBooktalkDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.BooktalkService;
import org.sophy.sophy.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
@Tag(name = "작가", description = "작가 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
public class AuthorController {

    private final MemberService memberService;
    private final BooktalkService booktalkService;

    @GetMapping("/my-booktalks")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "개설한 북토크 조회")
    public ApiResponseDto<List<MyPageBooktalkDto>> getAuthorBooktalks(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_AUTHOR_BOOKTALKS_SUCCESS,
            memberService.getAuthorBooktalksByEmail(user.getUsername()));
    }

    @PostMapping("/booktalk")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "북토크 생성")
    public ApiResponseDto<BooktalkCreateResponseDto> createBooktalk(
        @Valid @ModelAttribute BooktalkRequestDto booktalkRequestDto,
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.CREATE_BOOKTALK_SUCCESS,
            booktalkService.createBooktalk(booktalkRequestDto, user.getUsername()));
    }

    @PatchMapping("/booktalk/{booktalkId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "북토크 수정")
    public ApiResponseDto<BooktalkUpdateDto> updateBooktalk(
        @PathVariable("booktalkId") Long booktalkId,
        @Valid @RequestBody BooktalkUpdateDto booktalkUpdateDto) {
        return ApiResponseDto.success(SuccessStatus.PATCH_BOOKTALK_SUCCESS,
            booktalkService.updateBooktalk(booktalkId, booktalkUpdateDto));
    }

    @DeleteMapping("/booktalk/{booktalkId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "북토크 삭제")
    public ApiResponseDto<BooktalkDeleteResponseDto> deleteBooktalk(
        @PathVariable("booktalkId") Long booktalkId) {
        return ApiResponseDto.success(SuccessStatus.DELETE_BOOKTALK_SUCCESS,
            booktalkService.deleteBooktalk(booktalkId));
    }

    @PostMapping("/booktalk/{booktalkId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "북토크 완료")
    public ApiResponseDto<CompletedBooktalk> completeBooktalk(
        @PathVariable("booktalkId") Long booktalkId) {
        return ApiResponseDto.success(SuccessStatus.DELETE_BOOKTALK_SUCCESS,
            booktalkService.completeBooktalk(booktalkId));
    }
}
