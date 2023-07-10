package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.BooktalkUpdateDto;
import org.sophy.sophy.controller.dto.request.BooktalkRequestDto;
import org.sophy.sophy.controller.dto.response.BooktalkCreateResponseDto;
import org.sophy.sophy.controller.dto.response.BooktalkDeleteResponseDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.BooktalkService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booktalk")
public class BooktalkController {
    private final BooktalkService booktalkService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<BooktalkCreateResponseDto> createBooktalk(@Valid @RequestBody BooktalkRequestDto booktalkRequestDto) {
        return ApiResponseDto.success(SuccessStatus.CREATE_BOOKTALK_SUCCESS, booktalkService.createBooktalk(booktalkRequestDto));
    }

    @PatchMapping("/{booktalkId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<BooktalkUpdateDto> updateBooktalk(@PathVariable("booktalkId") Long booktalkId, @Valid @RequestBody BooktalkUpdateDto booktalkUpdateDto) {
        return ApiResponseDto.success(SuccessStatus.PATCH_BOOKTALK_SUCCESS, booktalkService.updateBooktalk(booktalkId, booktalkUpdateDto));
    }

    @DeleteMapping("/{booktalkId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<BooktalkDeleteResponseDto> deleteBooktalk(@PathVariable("booktalkId") Long booktalkId) {
        return ApiResponseDto.success(SuccessStatus.DELETE_BOOKTALK_SUCCESS, booktalkService.deleteBooktalk(booktalkId));
    }

}
