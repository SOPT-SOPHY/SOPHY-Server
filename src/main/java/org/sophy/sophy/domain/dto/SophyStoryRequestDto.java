package org.sophy.sophy.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SophyStoryRequestDto {
    @Schema(description = "연도", example = "2023")
    private Integer year;
    @Schema(description = "월", example = "7")
    private Integer month;
}
