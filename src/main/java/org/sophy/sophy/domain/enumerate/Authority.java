package org.sophy.sophy.domain.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Authority {
    ADMIN("ROLE_MEMBER,ROLE_AUTHOR,ROLE_OPERATOR"),
    MEMBER("ROLE_MEMBER"),
    AUTHOR("ROLE_MEMBER,ROLE_AUTHOR"),
    OPERATOR("ROLE_MEMBER,ROLE_OPERATOR"),
    ALL("ROLE_MEMBER,ROLE_AUTHOR,ROLE_OPERATOR");

    private String value;
}
