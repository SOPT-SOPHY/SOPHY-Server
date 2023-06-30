package org.sophy.sophy.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    // 보유하고 있는 jwtSecret Key를 인코딩하여 저장
    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder()
                .encodeToString(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 토큰 발급
    public String issuedToken(String userId) {
        final Date now = new Date();

        // 클레임 생성 (토큰의 payload에 저장됨, 서버에서 보낼 데이터가 담김)
        final Claims claims = Jwts.claims()
                .setSubject("access_token")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 120 * 60 * 1000L));

        //private claim 등록
        claims.put("userId", userId);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE , Header.JWT_TYPE) //header 파라미터 추가
                .setClaims(claims) //claim 추가
                .signWith(getSigningKey()) //서명에 쓸 secret key나 private key 지정
                .compact(); // 압축 및 서명
    }

    private Key getSigningKey() {
        final byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 검증
    public boolean verifyToken(String token) {
        try {
            final Claims claims = getBody(token);
            return true;
        } catch (RuntimeException e) {
            if (e instanceof ExpiredJwtException) {
                throw new UnauthorizedException(ErrorStatus.TOKEN_TIME_EXPIRED_EXCEPTION, ErrorStatus.TOKEN_TIME_EXPIRED_EXCEPTION.getMessage());
            }
            return false;
        }
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // 서명 검증을 위한 키  지정
                .build() //builder를 통해 jwt parser 리턴
                .parseClaimsJws(token) //토큰을 원래 claim으로 변환
                .getBody(); //body 받아오기
    }

    // JWT 토큰 내용 확인
    public String getJwtContents(String token) {
        final Claims claims = getBody(token);
        return (String) claims.get("userId");
    }
}
