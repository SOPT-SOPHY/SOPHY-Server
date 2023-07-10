package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.request.CityRequestDto;
import org.sophy.sophy.controller.dto.response.BooktalkResponseDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.BooktalkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("booktalk")
public class BooktalkController {
    private final BooktalkService booktalkService;

    @GetMapping
    public ApiResponseDto<List<BooktalkResponseDto>> getPlacesByCity(@RequestBody CityRequestDto cityRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_BOOKTALKS_BY_CITY_SUCCESS, booktalkService.getBooktalksByCity(cityRequestDto));
    }
}
