package org.sophy.sophy.controller;

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
public class AuthorController { private final MemberService memberService;
    private final BooktalkService booktalkService;

    @GetMapping("/my-booktalks") //개설한 북토크 조회
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<MyPageBooktalkDto>> getAuthorBooktalks(@AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_AUTHOR_BOOKTALKS_SUCCESS, memberService.getAuthorBooktalksByEmail(user.getUsername()));
    }

    @PostMapping("/booktalk") //북토크 생성
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<BooktalkCreateResponseDto> createBooktalk(@Valid @ModelAttribute BooktalkRequestDto booktalkRequestDto) {
        return ApiResponseDto.success(SuccessStatus.CREATE_BOOKTALK_SUCCESS, booktalkService.createBooktalk(booktalkRequestDto));
    }

    @PatchMapping("/booktalk/{booktalkId}") //북토크 수정
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<BooktalkUpdateDto> updateBooktalk(@PathVariable("booktalkId") Long booktalkId, @Valid @RequestBody BooktalkUpdateDto booktalkUpdateDto) {
        return ApiResponseDto.success(SuccessStatus.PATCH_BOOKTALK_SUCCESS, booktalkService.updateBooktalk(booktalkId, booktalkUpdateDto));
    }

    @DeleteMapping("/booktalk/{booktalkId}") //북토크 삭제
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<BooktalkDeleteResponseDto> deleteBooktalk(@PathVariable("booktalkId") Long booktalkId) {
        return ApiResponseDto.success(SuccessStatus.DELETE_BOOKTALK_SUCCESS, booktalkService.deleteBooktalk(booktalkId));
    }

    @PostMapping("/booktalk/{booktalkId}") //북토크 완료
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<CompletedBooktalk> completeBooktalk(@PathVariable("booktalkId") Long booktalkId) {
        return ApiResponseDto.success(SuccessStatus.DELETE_BOOKTALK_SUCCESS, booktalkService.completeBooktalk(booktalkId));
    }
}
