package org.sophy.sophy.domain.dto.place.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.Place;
import org.sophy.sophy.domain.enumerate.City;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceRequestDto {

    @NotNull
    @Schema(description = "지역 이름", example = "UIJEONGBU_DONG")
    private City city;
    @NotBlank
    @Schema(description = "공간 이름", example = "나의 공간")
    private String name;

    @NotBlank
    @Schema(description = "공간 주소", example = "경기도 의정부시 의정부동 의정부로 14번길 7")
    private String address;

    @NotNull
    @Schema(description = "최대 수용 인원", example = "12")
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
