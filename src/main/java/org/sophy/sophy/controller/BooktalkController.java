package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.domain.CompletedBooktalk;
import org.sophy.sophy.domain.dto.booktalk.BooktalkUpdateDto;
import org.sophy.sophy.domain.dto.booktalk.request.BooktalkParticipationRequestDto;
import org.sophy.sophy.domain.dto.booktalk.request.BooktalkRequestDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkCreateResponseDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkDeleteResponseDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkDetailResponseDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkResponseDto;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.BooktalkService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booktalk")
public class BooktalkController {
    private final BooktalkService booktalkService;

    @PostMapping //북토크 생성
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<BooktalkCreateResponseDto> createBooktalk(@Valid @ModelAttribute BooktalkRequestDto booktalkRequestDto) {
        return ApiResponseDto.success(SuccessStatus.CREATE_BOOKTALK_SUCCESS, booktalkService.createBooktalk(booktalkRequestDto));
    }

    @PatchMapping("/{booktalkId}") //북토크 수정
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<BooktalkUpdateDto> updateBooktalk(@PathVariable("booktalkId") Long booktalkId, @Valid @RequestBody BooktalkUpdateDto booktalkUpdateDto) {
        return ApiResponseDto.success(SuccessStatus.PATCH_BOOKTALK_SUCCESS, booktalkService.updateBooktalk(booktalkId, booktalkUpdateDto));
    }

    @DeleteMapping("/{booktalkId}") //북토크 삭제
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<BooktalkDeleteResponseDto> deleteBooktalk(@PathVariable("booktalkId") Long booktalkId) {
        return ApiResponseDto.success(SuccessStatus.DELETE_BOOKTALK_SUCCESS, booktalkService.deleteBooktalk(booktalkId));
    }

    @GetMapping("/search/{booktalkId}/detail") //북토크 상세 조회
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<BooktalkDetailResponseDto> getBooktalkDetail(@PathVariable("booktalkId") Long booktalkId) {
        return ApiResponseDto.success(SuccessStatus.GET_BOOKTALK_DETAIL_SUCCESS, booktalkService.getBooktalkDetail(booktalkId));
    }

    @GetMapping("/search/{city}") //지역으로 북토크 조회
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<BooktalkResponseDto>> getPlacesByCity(@Valid @PathVariable(name = "city") City city) {
        return ApiResponseDto.success(SuccessStatus.GET_BOOKTALKS_BY_CITY_SUCCESS, booktalkService.getBooktalksByCity(city));
    }

    @PostMapping("/participation") //북토크 참가
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto postBooktalkParticipation(@Valid @RequestBody BooktalkParticipationRequestDto booktalkParticipationRequestDto) {
        booktalkService.postBooktalkParticipation(booktalkParticipationRequestDto);
        return ApiResponseDto.success(SuccessStatus.CREATE_BOOKTALK_PARTICIPATION_SUCCESS);
    }

    @PostMapping("/{booktalkId}") //북토크 완료
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<CompletedBooktalk> completeBooktalk(@PathVariable("booktalkId") Long booktalkId) {
        return ApiResponseDto.success(SuccessStatus.DELETE_BOOKTALK_SUCCESS, booktalkService.completeBooktalk(booktalkId));
    }
}
