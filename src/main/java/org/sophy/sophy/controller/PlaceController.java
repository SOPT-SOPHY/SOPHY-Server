package org.sophy.sophy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.controller.dto.response.PlaceResponseDto;
import org.sophy.sophy.domain.dto.place.request.PlaceRequestDto;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
@Tag(name = "공간", description = "공간 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "공간 생성")
    public ApiResponseDto<PlaceResponseDto> createPlace(
        @Valid @ModelAttribute PlaceRequestDto placeRequestDto,
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.CREATE_PLACE_SUCCESS,
            placeService.createPlace(placeRequestDto, user.getUsername()));
    }

    @GetMapping("/search")
    @Operation(summary = "지역으로 공간 조회")
    public ApiResponseDto<List<PlaceResponseDto>> getPlacesByCities(
        @Parameter(description = "지역 이름", example = "UIJEONGBU_DONG") @Valid @RequestParam(name = "cities") List<City> cities) {
        return ApiResponseDto.success(SuccessStatus.GET_PLACES_BY_CITY_SUCCESS,
            placeService.getPlacesByCities(cities));
    }
}
