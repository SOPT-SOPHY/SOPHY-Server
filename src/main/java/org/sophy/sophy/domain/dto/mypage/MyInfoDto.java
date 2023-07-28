package org.sophy.sophy.domain.dto.mypage;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sophy.sophy.domain.enumerate.City;

@Getter
@AllArgsConstructor
@Builder
public class MyInfoDto {

    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String email;
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$", message = "닉네임 형식에 맞지 않습니다.")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String name;
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String phoneNum;
    @Schema(description = "성별", example = "female")
    private String gender;
    @Schema(description = "생년월일", example = "20000822")
    private String birth;
    @Schema(description = "지역", example = "UIJEONGBU_DONG")
    private City city;
    @Schema(description = "마케팅 수신 동의", example = "true")
    private Boolean marketingAgree;
}
