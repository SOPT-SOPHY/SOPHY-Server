package org.sophy.sophy.config.auth.common;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


//이 서비스를 통해 Auth Service중 authenticate 메서드 실행 과정중 필요한 UserDetails 발급 과정 정의
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
            .map(this::createUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    // DB에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
            member.getAuthority().toString());

        return new User(
            member.getEmail(),
            member.getPassword(),
            Collections.singleton(grantedAuthority)
        );
    }
}
