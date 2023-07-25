package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.domain.Book;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.MemberBooktalk;
import org.sophy.sophy.domain.dto.mypage.MyBookDto;
import org.sophy.sophy.domain.dto.mypage.MyPageBooktalkDto;
import org.sophy.sophy.domain.dto.mypage.MyPageDto;
import org.sophy.sophy.domain.dto.mypage.MyInfoDto;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MyPageDto getMyPage(Long memberId) {
        Member member = memberRepository.getMemberById(memberId);
        //여기에 추가로 member에 있는 userBookTalk 리스트를 시간순으로 정렬해 가장 마감이 임박한 booktalk도 보여줌
        if(member.getAuthority().equals(Authority.ROLE_AUTHOR)){
            return MyPageDto.builder()
                    .name(member.getName())
                    .expectedBookTalkCount(member.getAuthorProperty().getMyBookTalkList().size())
                    .waitingBookTalkCount(member.getUserBookTalkList().size())
                    .completeBookTalkCount(member.getCompletedBookTalkList().size())
                    .myPageBooktalkDtos(getBooktalksByMemberId(memberId))
                    .myBookDtos(getAuthorBooksByMemberId(memberId))
                    .build();
        } else {
            return MyPageDto.builder()
                    .name(member.getName())
                    .waitingBookTalkCount(member.getUserBookTalkList().size())
                    .completeBookTalkCount(member.getCompletedBookTalkList().size())
                    .myPageBooktalkDtos(getBooktalksByMemberId(memberId))
                    .build();
        }
    }
    @Transactional
    public MyInfoDto getMyInfo(Long memberId) {
        Member member = memberRepository.getMemberById(memberId);
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
        Member member = memberRepository.getMemberById(memberId);
        member.setAdditionalInfo(memberAdditionalInfoDto);
        return memberAdditionalInfoDto;
    }

    @Transactional
    public MyInfoDto patchMyInfo(Long memberId, MyInfoDto myInfoDto) {
        Member member = memberRepository.getMemberById(memberId);
        member.patchMyInfo(myInfoDto);
        return myInfoDto;
    }

    @Transactional
    public List<MyPageBooktalkDto>  getBooktalksByMemberId(Long memberId) { //예정된 북토크 조회 메서드
        List<MemberBooktalk> userBookTalkList = memberRepository.getMemberById(memberId).getUserBookTalkList();
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
                    .booktalkStatus(booktalk.getBooktalkStatus())
                    .build());
        });

        booktalkResponseDtoList.sort(Comparator.comparing(MyPageBooktalkDto::getEndDate));
        return booktalkResponseDtoList;
    }
    
    @Transactional
    public List<MyPageBooktalkDto> getAuthorBooktalksByMemberId(Long memberId) { //작가가 개최한 북토크 조회 메서드
        List<Booktalk> authorBookTalkList = memberRepository.getMemberById(memberId).getAuthorProperty().getMyBookTalkList();
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

    @Transactional
    public List<MyBookDto> getAuthorBooksByMemberId(Long memberId) { //작가가 쓴 책 조회 메서드
        List<Book> authorBookList = memberRepository.getMemberById(memberId).getAuthorProperty().getMyBookList();
        List<MyBookDto> bookResponseDtoList = new ArrayList<>();
        authorBookList.forEach(book -> {
            bookResponseDtoList.add(MyBookDto.builder()
                    .title(book.getTitle())
                    .bookCategory(book.getBookCategory())
                    .booktalkOpenCount(book.getBooktalkOpenCount())
                    .isRegistration(book.getIsRegistration())
                    .bookImageUrl(book.getBookImageUrl())
                    .build());
        });
        return bookResponseDtoList;
    }

}
