package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.request.CityRequestDto;
import org.sophy.sophy.controller.dto.response.BooktalkResponseDto;
import org.sophy.sophy.domain.BooktalkStatus;
import org.sophy.sophy.domain.City;
import org.sophy.sophy.domain.Place;
import org.sophy.sophy.infrastructure.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooktalkService {
    private final PlaceRepository placeRepository;

    public List<BooktalkResponseDto> getBooktalksByCity(CityRequestDto cityRequestDto) {
        City city = cityRequestDto.getCity();
        List<Place> placeList;

        if (city.equals(City.UIJEONGBU_SI)) {
            placeList = placeRepository.findAll();
        } else {
            placeList = placeRepository.findAllByCity(city);
        }

        List<BooktalkResponseDto> booktalkList = new ArrayList<>();
        placeList.forEach(place -> {
            place.getBooktalkList().forEach(booktalk -> {
                        // 모집중인 북토크만 추가
                        if (booktalk.getBooktalkStatus() == BooktalkStatus.RECRUITING) {
                            booktalkList.add(BooktalkResponseDto.of(booktalk));
                        }
                    }
            );
        });

        // 마감 임박순으로 정렬
        booktalkList.sort(Comparator.comparing(BooktalkResponseDto::getEndDate));

        return booktalkList;
    }
}
