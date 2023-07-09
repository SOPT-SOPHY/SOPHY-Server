package org.sophy.sophy.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.Place;

@Getter
@AllArgsConstructor
public class PlaceResponseDto {
    private String placeImage;
    private String name;
    private String address;
    private Integer maximum;

    public static PlaceResponseDto of(Place place) {
        return new PlaceResponseDto(
                place.getPlaceImage(),
                place.getName(),
                place.getAddress(),
                place.getMaximum());
    }
}
