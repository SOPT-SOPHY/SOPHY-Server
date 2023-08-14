package org.sophy.sophy.domain.dto;

import lombok.Getter;
import org.sophy.sophy.domain.enumerate.City;

import javax.validation.constraints.NotNull;

@Getter
public class CityRequestDto {

    @NotNull(message = "유효하지 않은 지역입니다.")
    private City city;
}
