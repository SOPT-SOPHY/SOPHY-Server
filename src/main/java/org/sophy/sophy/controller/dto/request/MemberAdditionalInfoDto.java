package org.sophy.sophy.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAdditionalInfoDto {

    @Schema(description = "성별", example = "female")
    private String gender;
    @Schema(description = "생년월일", example = "20000822")
    private String birth;
}
