package org.sophy.sophy.config.auth;

import java.util.Collections;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.config.auth.dto.OAuthAttributes;
import org.sophy.sophy.config.auth.dto.SessionUser;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth2 정보를 가져옵니다.

        //현재 로그인 진행 중인 서비스를 구분하는 코드 (구글 or 네이버 or 카카오 ...)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //OAuth2 로그인 진행 시 키가되는 필드 값, Primary Key와 같은 의미
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
            .getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
            oAuth2User.getAttributes());

        //SessionUser = 세션에 사용자 정보를 저장하기 위한 DTO 클래스
        Member member = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(member));

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(member.getAuthority().getKey())),
            attributes.getAttributes(),
            attributes.getNameAttributeKey()
        );
    }

    /**
     * 이미 존재하는 회원이라면 이름과 프로필이미지를 업데이트해줍니다.
     * 처음 가입하는 회원이라면 Member 테이블을 생성합니다. (소셜 회원가입)
     **/
    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
            .map(entity -> entity.update(attributes.getName()))
            .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }

}
