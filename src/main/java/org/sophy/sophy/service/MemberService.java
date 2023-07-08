package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.MyPageDto;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.NotFoundException;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MyPageDto getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        //여기에 추가로 member에 있는 userBookTalk 리스트를 시간순으로 정렬해 가장 마감이 임박한 booktalk도 보여줌
        if(member.isAuthor()){
            return MyPageDto.builder()
                    .name(member.getName())
                    .bookCount(member.getBookCount())
                    .bookTalkCount(member.getBookTalkCount())
                    .matchingBookTalkCount(member.getAuthor().getMatchingBookTalkCount())
                    .recruitBookTalkCount(member.getAuthor().getRecruitBookTalkCount())
                    .build();
        } else {
            return MyPageDto.builder()
                    .name(member.getName())
                    .bookCount(member.getBookCount())
                    .bookTalkCount(member.getBookTalkCount())
                    .build();
        }
    }
}
