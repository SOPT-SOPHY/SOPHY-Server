package org.sophy.sophy.config.auth;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.sophy.sophy.domain.enumerate.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private final String email;
    private final Authority authority;

    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     */
    public CustomOAuth2User(
        Collection<? extends GrantedAuthority> authorities,
        Map<String, Object> attributes, String nameAttributeKey,
        String email, Authority authority) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.authority = authority;
    }
}
