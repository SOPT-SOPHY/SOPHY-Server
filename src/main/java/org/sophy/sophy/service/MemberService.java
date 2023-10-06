package org.sophy.sophy.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.domain.Book;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.dto.mypage.MyBookDto;
import org.sophy.sophy.domain.dto.mypage.MyInfoDto;
import org.sophy.sophy.domain.dto.mypage.MyPageBooktalkDto;
import org.sophy.sophy.domain.dto.mypage.MyPageDto;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.external.client.aws.S3Service;
import org.sophy.sophy.infrastructure.BookRepository;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.infrastructure.query.BookQueryRepository;
import org.sophy.sophy.infrastructure.query.BooktalkQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BooktalkQueryRepository booktalkQueryRepository;
    private final BookQueryRepository bookQueryRepository;
    private final BookRepository bookRepository;
    private final S3Service s3Service;

    public MyPageDto geyMyPage(String email) {
        Member member = memberRepository.getMemberByEmail(email);

        if (member.getAuthority().equals(Authority.AUTHOR)) {
            return MyPageDto.builder()
                .profileImage(member.getProfileImage())
                .name(member.getName())
                .email(member.getEmail())
                .myBookCount(bookRepository.countBookByAuthorProperty(member.getAuthorProperty()))
                .build();
        }
        return MyPageDto.builder()
            .profileImage(member.getProfileImage())
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

    //예정된 북토크 메서드
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

    @Transactional
    public String patchProfileImage(String email, MultipartFile profileImage) {
        String profileImageUrl = s3Service.uploadImage(profileImage, "profile");
        memberRepository.getMemberByEmail(email).patchProfileImage(profileImageUrl);
        return profileImageUrl;
    }

}
