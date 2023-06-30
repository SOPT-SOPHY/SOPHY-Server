package org.sophy.sophy.controller.response.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseDto {
    private Long memberId;
    private String nickname;

    public static MemberResponseDto of(Long memberId, String nickname) {
        return new MemberResponseDto(memberId, nickname);
    }
}
