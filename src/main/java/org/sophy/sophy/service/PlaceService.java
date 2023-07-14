package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.dto.CityRequestDto;
import org.sophy.sophy.controller.dto.response.PlaceResponseDto;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.infrastructure.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public List<PlaceResponseDto> getPlacesByCity(City city) {
        //의정부 시이면 전체 조회
        if (city == City.UIJEONGBU_SI) {
            return placeRepository.findAll().stream()
                    .map(PlaceResponseDto::of)
                    .collect(Collectors.toList());
        }
        return placeRepository.findAllByCity(city).stream()
                .map(PlaceResponseDto::of)
                .collect(Collectors.toList());
    }
}
