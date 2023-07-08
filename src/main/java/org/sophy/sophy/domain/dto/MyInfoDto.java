package org.sophy.sophy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sophy.sophy.domain.City;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyInfoDto {
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$", message = "닉네임 형식에 맞지 않습니다.")
    private String name;
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phoneNum;
    private String gender;
    private String birth;
    private City city;
    private boolean marketingAgree;
}
