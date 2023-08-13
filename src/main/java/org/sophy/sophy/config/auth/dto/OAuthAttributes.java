package org.sophy.sophy.config.auth.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.enumerate.Authority;

@Getter
@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
        Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver(attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao(attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
        Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    private static OAuthAttributes ofNaver(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .attributes(attributes)
            .nameAttributeKey("id")
            .build();
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .attributes(attributes)
            .nameAttributeKey("id")
            .build();
    }

    public Member toEntity() {
        return Member.builder()
            .name(name)
            .email(email)
            .authority(Authority.GUEST)
            .build();
    }
}
