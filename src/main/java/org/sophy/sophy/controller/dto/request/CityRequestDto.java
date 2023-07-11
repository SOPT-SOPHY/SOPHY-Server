package org.sophy.sophy.controller.dto.request;

import lombok.Getter;
import org.sophy.sophy.domain.City;

import javax.validation.constraints.NotNull;

@Getter
public class CityRequestDto {
    @NotNull
    private City city;
}
