package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.MemberBooktalk;
import org.sophy.sophy.domain.dto.MyPageBooktalkDto;
import org.sophy.sophy.domain.dto.MyPageDto;
import org.sophy.sophy.domain.dto.MyInfoDto;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.NotFoundException;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MyPageDto getMyPage(Long memberId) {
        Member member = getMemberById(memberId);
        //여기에 추가로 member에 있는 userBookTalk 리스트를 시간순으로 정렬해 가장 마감이 임박한 booktalk도 보여줌
        if(member.getIsAuthor()){
            return MyPageDto.builder()
                    .name(member.getName())
                    .expectedBookTalkCount(member.getAuthorProperty().getExpectedBookTalkCount())
                    .waitingBookTalkCount(member.getWaitingBookTalkCount())
                    .completeBookTalkCount(member.getCompleteBookTalkCount())
                    .myPageBooktalkDtos(getBooktalksByMemberId(memberId))
                    .build();
        } else {
            return MyPageDto.builder()
                    .name(member.getName())
                    .waitingBookTalkCount(member.getWaitingBookTalkCount())
                    .completeBookTalkCount(member.getCompleteBookTalkCount())
                    .myPageBooktalkDtos(getBooktalksByMemberId(memberId))
                    .build();
        }
    }
    @Transactional
    public MyInfoDto getMyInfo(Long memberId) {
        Member member = getMemberById(memberId);
        return MyInfoDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phoneNum(member.getPhoneNum())
                .gender(member.getGender())
                .birth(member.getBirth())
                .city(member.getMyCity())
                .marketingAgree(member.getMarketingAgree())
                .build();
    }
    @Transactional
    public MemberAdditionalInfoDto postAdditionalInfo(Long memberId, MemberAdditionalInfoDto memberAdditionalInfoDto) {
        Member member = getMemberById(memberId);
        member.setAdditionalInfo(memberAdditionalInfoDto);
        return memberAdditionalInfoDto;
    }

    @Transactional
    public MyInfoDto patchMyInfo(Long memberId, MyInfoDto myInfoDto) {
        Member member = getMemberById(memberId);
        member.patchMyInfo(myInfoDto);
        return myInfoDto;
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }

    @Transactional
    public List<MyPageBooktalkDto>  getBooktalksByMemberId(Long memberId) { //예정된 북토크 조회 메서드
        List<MemberBooktalk> userBookTalkList = getMemberById(memberId).getUserBookTalkList();
        List<MyPageBooktalkDto> booktalkResponseDtoList = new ArrayList<>();
        userBookTalkList.forEach(memberBooktalk -> {
            Booktalk booktalk = memberBooktalk.getBooktalk();
            booktalkResponseDtoList.add(MyPageBooktalkDto.builder()
                    .booktalkId(booktalk.getId())
                    .booktalkImageUrl(booktalk.getBooktalkImageUrl())
                    .title(booktalk.getTitle())
                    .author(booktalk.getMember().getName())
                    .startDate(booktalk.getStartDate())
                    .endDate(booktalk.getEndDate())
                    .place(booktalk.getPlace().getName())
                    .participant(booktalk.getParticipantList().size())
                    .maximum(booktalk.getMaximum())
                    .build());
        });
        return booktalkResponseDtoList;
    }

    @Transactional
    public List<MyPageBooktalkDto> getAuthorBooktalksByMemberId(Long memberId) { //작가 북토크 조회 메서드
        List<Booktalk> authorBookTalkList = getMemberById(memberId).getAuthorProperty().getMyBookTalkList();
        List<MyPageBooktalkDto> booktalkResponseDtoList = new ArrayList<>();
        authorBookTalkList.forEach(booktalk -> {
            booktalkResponseDtoList.add(MyPageBooktalkDto.builder()
                    .booktalkId(booktalk.getId())
                    .booktalkImageUrl(booktalk.getBooktalkImageUrl())
                    .title(booktalk.getTitle())
                    .author(booktalk.getMember().getName())
                    .startDate(booktalk.getStartDate())
                    .endDate(booktalk.getEndDate())
                    .place(booktalk.getPlace().getName())
                    .participant(booktalk.getParticipantList().size())
                    .maximum(booktalk.getMaximum())
                    .booktalkStatus(booktalk.getBooktalkStatus())
                    .build());
        });
        return booktalkResponseDtoList;
    }

}
