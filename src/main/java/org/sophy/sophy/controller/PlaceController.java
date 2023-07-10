package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.request.CityRequestDto;
import org.sophy.sophy.controller.dto.response.PlaceResponseDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.PlaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public ApiResponseDto<List<PlaceResponseDto>> getPlacesByCity(@RequestBody CityRequestDto cityRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_PLACES_BY_CITY_SUCCESS, placeService.getPlacesByCity(cityRequestDto));
    }
}
