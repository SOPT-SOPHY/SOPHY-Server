package org.sophy.sophy.service;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.domain.Book;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.dto.mypage.MyBookDto;
import org.sophy.sophy.domain.dto.mypage.MyPageBooktalkDto;
import org.sophy.sophy.domain.dto.mypage.MyInfoDto;
import org.sophy.sophy.domain.dto.mypage.MySophyDto;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.infrastructure.BookRepository;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.infrastructure.query.BookQueryRepository;
import org.sophy.sophy.infrastructure.query.BooktalkQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BooktalkQueryRepository booktalkQueryRepository;
    private final BookQueryRepository bookQueryRepository;
    private final BookRepository bookRepository;


//    @Transactional
//    public MyPageDto getMyPage(String email) {
//        Member member = memberRepository.getMemberByEmail(email);
//        //여기에 추가로 member에 있는 userBookTalk 리스트를 시간순으로 정렬해 가장 마감이 임박한 booktalk도 보여줌
//        if (member.getAuthority().equals(Authority.AUTHOR)) {
//            return MyPageDto.builder()
//                .name(member.getName())
//                .expectedBookTalkCount(member.getAuthorProperty().getMyBookTalkSize())
//                .waitingBookTalkCount(member.getUserBookTalkSize()) //쿼리 나감 (공통 부분 묶어서 fetch join 혹은 dto 쿼리로 가져오고 작가 부분만 setter 이용해 변경해야 할듯)
//                .completeBookTalkCount(member.getCompletedBookTalkSize()) //쿼리 나감
//                .myPageBooktalkDtos(getBooktalksByMember(member))
//                .myBookDtos(getAuthorBooksByMember(member))
//                .build();
//        } else {
//            return MyPageDto.builder()
//                .name(member.getName())
//                .waitingBookTalkCount(member.getUserBookTalkSize())
//                .completeBookTalkCount(member.getCompletedBookTalkSize())
//                .myPageBooktalkDtos(getBooktalksByMember(member))
//                .build();
//        }
//    }

    public MySophyDto geyMySophy(String email) {
        Member member = memberRepository.getMemberByEmail(email);

        if (member.getAuthority().equals(Authority.AUTHOR)) {
            return MySophyDto.builder()
                    .name(member.getName())
                    .email(member.getEmail())
                    .myBookCount(bookRepository.countBookByAuthorProperty(member.getAuthorProperty()))
                    .build();
        }
        return MySophyDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }

    @Transactional
    public MyInfoDto getMyInfo(String email) {
        return MyInfoDto.toBuild(memberRepository.getMemberByEmail(email));
    }

    @Transactional
    public MemberAdditionalInfoDto postAdditionalInfo(String email,
        MemberAdditionalInfoDto memberAdditionalInfoDto) {
        memberRepository.getMemberByEmail(email).setAdditionalInfo(memberAdditionalInfoDto);
        return memberAdditionalInfoDto;
    }

    @Transactional
    public MyInfoDto patchMyInfo(String email, MyInfoDto myInfoDto) {
        memberRepository.getMemberByEmail(email).patchMyInfo(myInfoDto);
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
        List<Long> bookIds = member.getAuthorProperty().getMyBookList()
            .stream().map(Book::getId)
            .collect(Collectors.toList());

        return bookQueryRepository.findBooks(bookIds);
    }

}
