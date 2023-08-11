package org.sophy.sophy.config.auth.dto;

import java.io.Serializable;
import lombok.Getter;
import org.sophy.sophy.domain.Member;

@Getter
public class SessionUser implements Serializable {
    private final String name;
    private final String email;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
    }

}
