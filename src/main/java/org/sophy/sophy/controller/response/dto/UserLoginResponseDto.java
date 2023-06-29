package org.sophy.sophy.controller.response.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLoginResponseDto {
    private Long userId;
    private String accessToken;

    public static UserLoginResponseDto of(Long userId, String accessToken) {
        return new UserLoginResponseDto(userId, accessToken);
    }
}
