package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.response.PlaceResponseDto;
import org.sophy.sophy.domain.City;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.BadRequestException;
import org.sophy.sophy.exception.model.NotFoundException;
import org.sophy.sophy.infrastructure.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public List<PlaceResponseDto> getPlacesByCity(String city) {
        try {
            City cityValue = City.valueOf(city);
            //의정부 시이면 전체 조회
            if (cityValue == City.UIJEONGBU_SI) {
                return placeRepository.findAll().stream()
                        .map(PlaceResponseDto::of)
                        .collect(Collectors.toList());
            }
            return placeRepository.findAllByCity(cityValue).stream()
                    .map(PlaceResponseDto::of)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException(ErrorStatus.NOT_FOUND_CITY_EXCEPTION, ErrorStatus.NOT_FOUND_CITY_EXCEPTION.getMessage());
        }
    }
}
