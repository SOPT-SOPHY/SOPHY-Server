package org.sophy.sophy.domain.enumerate;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반 사용자"),
    AUTHOR("ROLE_AUTHOR", "작가"),
    OPERATOR("ROLE_OPERATOR", "공간운영자"),
    ALL("ROLE_ALL", "작가 + 공간운영자");

    private final String key;
    private final String title;
}
