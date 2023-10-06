package org.sophy.sophy.jwt;

import static org.sophy.sophy.jwt.JwtFilter.AUTHORIZATION_HEADER;
import static org.sophy.sophy.jwt.JwtFilter.BEARER_PREFIX;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.sophy.sophy.external.client.oauth2.CustomOAuth2User;
import org.sophy.sophy.controller.dto.response.TokenDto;
import org.sophy.sophy.exception.model.SophyJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Slf4j
@Component
public class TokenProvider {

    public static final String REFRESH_HEADER = "Refresh";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final Long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L;
    private static final Long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7L;
    private final Key key;

    //빈 생성 때 key 값 세팅
    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public long getRefreshTokenExpireTime() {
        return REFRESH_TOKEN_EXPIRE_TIME;
    }

    //로그인 시
    public TokenDto generateTokenDto(Authentication authentication) {
        //권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        long now = (new Date()).getTime();

        //Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken;
        if(authentication.getClass() == UsernamePasswordAuthenticationToken.class){
            accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // payload "sub" : "name"
                .claim(AUTHORITIES_KEY, authorities) // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn) //payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        } else { //oauth2 로 로그인 했을 때 토큰 저장
            CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            accessToken = Jwts.builder()
                .setSubject(customOAuth2User.getEmail()) // payload "sub" : "name"
                .claim(AUTHORITIES_KEY, authorities) // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn) //payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        }

        //Refresh Token 생성
        String refreshToken = Jwts.builder()
            .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        return TokenDto.builder()
            .grantType(BEARER_TYPE)
            .accessToken(accessToken)
            .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
            .refreshToken(refreshToken)
            .build();
    }

    public String generateAccessToken(Authentication authentication) {
        //권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        long now = (new Date()).getTime();

        //Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        return Jwts.builder()
            .setSubject(customOAuth2User.getEmail()) // payload "sub" : "name"
            .claim(AUTHORITIES_KEY, authorities) // payload "auth": "ROLE_USER"
            .setExpiration(accessTokenExpiresIn) //payload "exp": 1516239022 (예시)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        //토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new SophyJwtException(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다.");
        }

        //클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims parseClaims(String accessToken) {
        try {
            //parser builder를 만들고 accesstoken을 넣어 claim(본문 내용) 획득
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException |
                 MalformedJwtException e) { //토큰 형식이 잘못됨
            log.info("잘못된 JWT 서명입니다.");
            throw new SophyJwtException(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) { //이 버전에서 지원하지 않는 JWT 토큰
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new SophyJwtException(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalStateException e) { //토큰의 형식이 잘못됨
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new SophyJwtException(HttpStatus.UNAUTHORIZED, "JWT 토큰이 잘못되었습니다.");
        }
    }

    public Long getExpiration(String accessToken) {
        //access Token 남은 유효시간
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(accessToken).getBody().getExpiration();
        long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    // Request Header에 Access Token 정보를 추출하는 메서드
    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Request Header에 Refresh Token 정보를 추출하는 메서드
    public String resolveRefreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(REFRESH_HEADER);
        if (StringUtils.hasText(bearerToken)) {
            return bearerToken;
        }
        return null;
    }
}
