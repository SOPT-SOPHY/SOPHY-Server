package org.sophy.sophy.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLoginRequestDto {
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank
    private String email;

    @NotNull
    @Pattern(
            regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다"
    )
    private String password;

    @NotEmpty(message = "accessToken 만료시간을 설정해주세요.")
    private long accessTokenExpiredTime;
    @NotEmpty(message = "refreshToken 만료시간을 설정해주세요.")
    private long refreshTokenExpiredTime;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
