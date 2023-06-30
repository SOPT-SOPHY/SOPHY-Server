package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.request.MemberLoginRequestDto;
import org.sophy.sophy.controller.dto.request.MemberRequestDto;
import org.sophy.sophy.controller.dto.response.MemberResponseDto;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.InvalidPasswordException;
import org.sophy.sophy.exception.model.NotFoundException;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long login(MemberLoginRequestDto request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if (!member.getPassword().equals(request.getPassword())) {
            throw new InvalidPasswordException(ErrorStatus.INVALID_PASSWORD_EXCEPTION, ErrorStatus.INVALID_PASSWORD_EXCEPTION.getMessage());
        }

        return member.getId();
    }

    @Transactional
    public MemberResponseDto create(MemberRequestDto request) {
        System.out.println(1);
        Member member = Member.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .isAuthor(false)
                .password(request.getPassword())
                .build();
        System.out.println(1);
        memberRepository.save(member);

        return MemberResponseDto.of(member.getId(), member.getNickname());
    }
}
