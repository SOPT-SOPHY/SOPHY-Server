package org.sophy.sophy.domain.dto;

import lombok.Getter;
import org.sophy.sophy.domain.enumerate.City;

import javax.validation.constraints.NotNull;

@Getter
public class CityRequestDto {
    @NotNull
    private City city;
}
