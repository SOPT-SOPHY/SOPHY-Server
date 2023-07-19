package org.sophy.sophy.domain.dto.place.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.Place;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceResponseDto {
    private Long placeId;
    private String name;

    public static PlaceResponseDto of(Place place) {
        return new PlaceResponseDto(place.getId(), place.getName());
    }
}
