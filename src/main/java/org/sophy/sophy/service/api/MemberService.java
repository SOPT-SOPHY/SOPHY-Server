package org.sophy.sophy.service.api;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.domain.Book;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.dto.mypage.MyBookDto;
import org.sophy.sophy.domain.dto.mypage.MyPageBooktalkDto;
import org.sophy.sophy.domain.dto.mypage.MyPageDto;
import org.sophy.sophy.domain.dto.mypage.MyInfoDto;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.infrastructure.query.BooktalkQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BooktalkQueryRepository booktalkQueryRepository;

    @Transactional
    public MyPageDto getMyPage(String email) {
        Member member = memberRepository.getMemberByEmail(email);
        //여기에 추가로 member에 있는 userBookTalk 리스트를 시간순으로 정렬해 가장 마감이 임박한 booktalk도 보여줌
        if (member.getAuthority().equals(Authority.ROLE_AUTHOR)) {
            return MyPageDto.builder()
                .name(member.getName())
                .expectedBookTalkCount(member.getAuthorProperty().getMyBookTalkList().size())
                .waitingBookTalkCount(member.getUserBookTalkList().size()) //쿼리 나감 (공통 부분 묶어서 fetch join 혹은 dto 쿼리로 가져오고 작가 부분만 setter 이용해 변경해야 할듯)
                .completeBookTalkCount(member.getCompletedBookTalkList().size()) //쿼리 나감
                .myPageBooktalkDtos(getBooktalksByMember(member))
                .myBookDtos(getAuthorBooksByMember(member))
                .build();
        } else {
            return MyPageDto.builder()
                .name(member.getName())
                .waitingBookTalkCount(member.getUserBookTalkList().size())
                .completeBookTalkCount(member.getCompletedBookTalkList().size())
                .myPageBooktalkDtos(getBooktalksByMember(member))
                .build();
        }
    }

    @Transactional
    public MyInfoDto getMyInfo(String email) {
        Member member = memberRepository.getMemberByEmail(email);
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
    public MemberAdditionalInfoDto postAdditionalInfo(String email,
        MemberAdditionalInfoDto memberAdditionalInfoDto) {
        Member member = memberRepository.getMemberByEmail(email);
        member.setAdditionalInfo(memberAdditionalInfoDto);
        return memberAdditionalInfoDto;
    }

    @Transactional
    public MyInfoDto patchMyInfo(String email, MyInfoDto myInfoDto) {
        Member member = memberRepository.getMemberByEmail(email);
        member.patchMyInfo(myInfoDto);
        return myInfoDto;
    }

    @Transactional
    public List<MyPageBooktalkDto> getBooktalksByMember(Member member) { //예정된 북토크 조회 메서드
        List<Long> booktalkIds = member.getUserBookTalkList()
            .stream().map(b -> b.getBooktalk().getId())
            .collect(Collectors.toList());

        return booktalkQueryRepository.findBooktalks(booktalkIds);
    }

    @Transactional
    public List<MyPageBooktalkDto> getAuthorBooktalksByEmail(String email) { //작가가 개최한 북토크 조회 메서드
        List<Long> booktalkIds = memberRepository.getMemberByEmail(email)
            .getAuthorProperty().getMyBookTalkList()
            .stream().map(Booktalk::getId)
            .collect(Collectors.toList());

        return booktalkQueryRepository.findBooktalks(booktalkIds);
    }

    @Transactional
    public List<MyBookDto> getAuthorBooksByMember(Member member) { //작가가 쓴 책 조회 메서드
        List<Book> authorBookList = member.getAuthorProperty().getMyBookList();
        List<MyBookDto> bookResponseDtoList = new ArrayList<>();
        authorBookList.forEach(book -> bookResponseDtoList.add(MyBookDto.builder()
            .title(book.getTitle())
            .bookCategory(book.getBookCategory())
            .booktalkOpenCount(book.getBooktalkOpenCount())
            .isRegistration(book.getIsRegistration())
            .bookImageUrl(book.getBookImageUrl())
            .build()));
        return bookResponseDtoList;
    }

}
