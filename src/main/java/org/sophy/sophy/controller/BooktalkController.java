package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.request.BooktalkRequestDto;
import org.sophy.sophy.controller.dto.response.BooktalkCreateResponseDto;
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

}
