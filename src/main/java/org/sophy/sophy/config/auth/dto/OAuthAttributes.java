package org.sophy.sophy.config.auth.dto;

import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.enumerate.Authority;

@Getter
@Builder
public class OAuthAttributes {
    private String nameAttributeKey;
    private OAuth2UserInfo oAuth2UserInfo;

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
            .nameAttributeKey(userNameAttributeName)
            .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
            .build();
    }

    private static OAuthAttributes ofNaver(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .nameAttributeKey("id")
            .oAuth2UserInfo(new NaverOAuth2UserInfo(attributes))
            .build();
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .nameAttributeKey("id")
            .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
            .build();
    }

    public Member toEntity(OAuth2UserInfo oAuth2UserInfo) {
        return Member.builder()
            .name(oAuth2UserInfo.getNickname())
            .socialId(oAuth2UserInfo.getId())
            .email(UUID.randomUUID() + "@socialUser.com")
            .authority(Authority.GUEST)
            .build();
    }
}
