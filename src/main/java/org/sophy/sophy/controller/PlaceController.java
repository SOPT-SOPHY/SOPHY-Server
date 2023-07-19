package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.response.PlaceResponseDto;
import org.sophy.sophy.domain.dto.place.request.PlaceRequestDto;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping //공간 생성
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<PlaceResponseDto> createPlace(@Valid @ModelAttribute PlaceRequestDto placeRequestDto) {
        return ApiResponseDto.success(SuccessStatus.CREATE_PLACE_SUCCESS, placeService.createPlace(placeRequestDto));
    }

    @GetMapping("/search/{city}") //지역으로 공간 조회
    public ApiResponseDto<List<PlaceResponseDto>> getPlacesByCity(@Valid @PathVariable(name = "city") City city) {
        return ApiResponseDto.success(SuccessStatus.GET_PLACES_BY_CITY_SUCCESS, placeService.getPlacesByCity(city));
    }
}
