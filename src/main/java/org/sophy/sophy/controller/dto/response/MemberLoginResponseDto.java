package org.sophy.sophy.controller.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLoginResponseDto {
    private Long memberId;
    private String accessToken;

    public static MemberLoginResponseDto of(Long memberId, String accessToken) {
        return new MemberLoginResponseDto(memberId, accessToken);
    }
}
