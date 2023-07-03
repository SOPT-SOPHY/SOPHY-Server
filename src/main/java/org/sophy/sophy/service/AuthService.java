package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.request.MemberLoginRequestDto;
import org.sophy.sophy.controller.dto.request.MemberRequestDto;
import org.sophy.sophy.controller.dto.request.TokenRequestDto;
import org.sophy.sophy.controller.dto.response.MemberResponseDto;
import org.sophy.sophy.controller.dto.response.TokenDto;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.ExistEmailException;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.jwt.TokenProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;

    //이메일 중복 체크만 가능한 서비스 하나 추가
    
    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new ExistEmailException(ErrorStatus.ALREADY_EXIST_USER_EXCEPTION, ErrorStatus.ALREADY_EXIST_USER_EXCEPTION.getMessage());
        }

        Member member = memberRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public String duplCheck(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new ExistEmailException(ErrorStatus.ALREADY_EXIST_USER_EXCEPTION, ErrorStatus.ALREADY_EXIST_USER_EXCEPTION.getMessage());
        }
        return "사용 가능한 이메일입니다.";
    }

    @Transactional
    public TokenDto login(MemberLoginRequestDto memberLoginRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberLoginRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        // ID 존재 여부 + 해당 ID로 불러온 비밀번호가 사용자가 제출한 비밀번호와 일치하는지 겅즘
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        redisTemplate.opsForValue().set("RT:" + authentication.getName(),
                        tokenDto.getRefreshToken(),
                        tokenProvider.getRefreshTokenExpireTime(),
                        TimeUnit.MILLISECONDS);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto){
        // 1. Refresh Token 검증
        tokenProvider.validateToken(tokenRequestDto.getRefreshToken());

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());

        // 4. Refresh Token 일치하는지 검사
        if(!refreshToken.equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token의 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        redisTemplate.opsForValue().set("RT:" + authentication.getName(),
                tokenDto.getRefreshToken(),
                tokenProvider.getRefreshTokenExpireTime(),
                TimeUnit.MILLISECONDS);

        //토큰발급
        return tokenDto;
    }

}
