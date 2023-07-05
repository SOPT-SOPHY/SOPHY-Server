package org.sophy.sophy.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class TokenRequestDto {
    @NotEmpty(message = "access 토큰을 입력해주세요")
    private String accessToken;
    @NotEmpty(message = "Refresh 토큰을 입력해주세요")
    private String refreshToken;

    @NotEmpty(message = "accessToken 만료시간을 설정해주세요.")
    private Long accessTokenExpiredTime;
    @NotEmpty(message = "refreshToken 만료시간을 설정해주세요.")
    private Long refreshTokenExpiredTime;
}
