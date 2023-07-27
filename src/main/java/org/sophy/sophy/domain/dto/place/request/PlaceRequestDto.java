package org.sophy.sophy.domain.dto.place.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.Place;
import org.sophy.sophy.domain.enumerate.City;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceRequestDto {
    @NotNull
    private City city;
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private Integer maximum;

    private MultipartFile placeImage;

    public Place toPlace(Member member, String placeImageUrl) {
        return Place.builder()
                .city(city)
                .member(member)
                .name(name)
                .address(address)
                .maximum(maximum)
                .placeImage(placeImageUrl)
                .build();
    }
}
