package org.sophy.sophy.service.api;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.*;
import org.sophy.sophy.domain.dto.booktalk.BooktalkUpdateDto;
import org.sophy.sophy.domain.dto.booktalk.request.BooktalkParticipationRequestDto;
import org.sophy.sophy.domain.dto.booktalk.request.BooktalkRequestDto;
import org.sophy.sophy.domain.dto.booktalk.response.*;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.ForbiddenException;
import org.sophy.sophy.external.client.aws.S3Service;
import org.sophy.sophy.exception.model.OverMaxParticipationException;
import org.sophy.sophy.infrastructure.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooktalkService {

    private final BooktalkRepository booktalkRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final MemberBooktalkRepository memberBooktalkRepository;
    private final CompletedBooktalkRepository completedBooktalkRepository;
    private final S3Service s3Service;

    // 북토크 생성
    @Transactional
    public BooktalkCreateResponseDto createBooktalk(BooktalkRequestDto booktalkRequestDto,
        String email) {
        Place place = placeRepository.getPlaceById(booktalkRequestDto.getPlaceId());
        Member member = memberRepository.getMemberByEmail(email);
        if (!member.getAuthority().equals(Authority.ROLE_AUTHOR)) {
            throw new ForbiddenException(ErrorStatus.FORBIDDEN_USER_EXCEPTION,
                ErrorStatus.FORBIDDEN_USER_EXCEPTION.getMessage());
        }

        String booktalkImageUrl = null;
        if (!booktalkRequestDto.getBooktalkImage().isEmpty()) {
            booktalkImageUrl = s3Service.uploadImage(booktalkRequestDto.getBooktalkImage(),
                "image");
        }
        Booktalk booktalk = booktalkRequestDto.toBooktalk(place, member, booktalkImageUrl);
        //작가 정보에 북토크 업데이트
        member.getAuthorProperty().getMyBookTalkList().add(booktalk);
        return BooktalkCreateResponseDto.of(booktalkRepository.save(booktalk));
    }

    // 북토크 수정
    @Transactional
    public BooktalkUpdateDto updateBooktalk(Long booktalkId, BooktalkUpdateDto booktalkUpdateDto) {
        Booktalk booktalk = booktalkRepository.getBooktalkById(booktalkId);
        booktalk.patchBooktalk(booktalkUpdateDto,
            placeRepository.getPlaceById(booktalkUpdateDto.getPlaceId()));
        return booktalkUpdateDto;
    }

    // 북토크 삭제
    @Transactional
    public BooktalkDeleteResponseDto deleteBooktalk(
        Long booktalkId) { // 수정필요 -> 테이블 외래키 고려하여 관련된 엔티티 전부 삭제해야 함
        Booktalk booktalk = booktalkRepository.getBooktalkById(booktalkId);
        //TODO soft delete?
        //공간이 거절 됐거나 공간 매칭중일 때만 삭제가능
        booktalk.getPlace().deleteBooktalk(booktalk);
        booktalk.getMember().getAuthorProperty().deleteBooktalk(booktalk);
        booktalkRepository.deleteById(booktalkId);
        return BooktalkDeleteResponseDto.of(booktalkId);
    }

    // 북토크 상세 조회
    public BooktalkDetailResponseDto getBooktalkDetail(Long booktalkId) {
        Booktalk booktalk = booktalkRepository.getBooktalkById(booktalkId);
        return BooktalkDetailResponseDto.of(booktalk);
    }

    // 북토크 참여 신청
    @Transactional
    public void postBooktalkParticipation(
        BooktalkParticipationRequestDto booktalkParticipationRequestDto, String email) {
        Member member = memberRepository.getMemberByEmail(email);
        Booktalk booktalk = booktalkRepository.getBooktalkById(
            booktalkParticipationRequestDto.getBooktalkId());
        //북토크 현재 인원이 최대인원을 넘지 않았는지 체크하는 메서드 필요할듯
        if (booktalk.getMaximum() == booktalk.getParticipantList().size()) {
            throw new OverMaxParticipationException(ErrorStatus.OVER_MAX_PARTICIPATION_EXCEPTION,
                ErrorStatus.OVER_MAX_PARTICIPATION_EXCEPTION.getMessage());
        }
        // 복합키?
        MemberBooktalk memberBooktalk = booktalkParticipationRequestDto.toMemberBooktalk(booktalk,
            member);
        //연관 객체 변경 ( member 객체 북토크 수 표시하는 메서드 리팩터 필요 )
        booktalk.getParticipantList().add(memberBooktalk);
        member.getUserBookTalkList().add(memberBooktalk);
        memberBooktalkRepository.save(memberBooktalk);
    }

    // 마감임박 북토크 조회
    public List<BooktalkDeadlineUpcomingDto> getBooktalkDeadlineUpcoming() {
        List<Place> placeList = placeRepository.findAll();

        List<BooktalkDeadlineUpcomingDto> booktalkList = new ArrayList<>();
        placeList.forEach(place -> {
            place.getBooktalkList().forEach(booktalk -> {
                    // 모집중인 북토크만 추가
                    if (booktalk.getBooktalkStatus() == BooktalkStatus.RECRUITING) {
                        booktalkList.add(BooktalkDeadlineUpcomingDto.of(booktalk));
                    }
                }
            );
        });

        // 마감 임박순으로 정렬
        booktalkList.sort(Comparator.comparing(BooktalkDeadlineUpcomingDto::getEndDate));

        return booktalkList;

    }

    //지역으로 북토크 조회
    @Transactional
    public List<BooktalkResponseDto> getBooktalksByCity(City city) {

        List<Booktalk> booktalks;

        if (city.equals(City.UIJEONGBU_SI)) {
            booktalks = booktalkRepository.findAll();
        } else {
            booktalks = booktalkRepository.findAllByCity(city);
        }

        List<BooktalkResponseDto> booktalkList = new ArrayList<>();
        booktalks.forEach(booktalk -> {
            // 모집중인 북토크만 추가
            if (booktalk.getBooktalkStatus() == BooktalkStatus.RECRUITING) {
                booktalkList.add(BooktalkResponseDto.of(booktalk));
            }
        });

        // 마감 임박순으로 정렬
        booktalkList.sort(Comparator.comparing(BooktalkResponseDto::getEndDate));

        return booktalkList;
    }

    @Transactional
    public CompletedBooktalk completeBooktalk(Long booktalkId) { //작가가 자신의 북토크를 완료상태로 변경하는 메서드
        Booktalk booktalk = booktalkRepository.getBooktalkById(booktalkId);
        booktalk.setBooktalkStatus(BooktalkStatus.COMPLETED);
        for (MemberBooktalk memberBooktalk : booktalk.getParticipantList()) { //참가 인원들 소피스토리 세팅
            Member member = memberBooktalk.getMember();
            CompletedBooktalk completedBooktalk = CompletedBooktalk.builder() // 완료된 북토크로 이동
                .title(booktalk.getTitle())
                .bookName(booktalk.getBook().getTitle())
                .authorName(booktalk.getMember().getName())
                .booktalkDate(booktalk.getEndDate())
                .placeName(booktalk.getPlace().getName())
                .bookCategory(booktalk.getBookCategory())
                .build();
            completedBooktalkRepository.save(completedBooktalk);
            member.getCompletedBookTalkList().add(completedBooktalk);
            completedBooktalk.setMember(member);
        }
        Member member = booktalk.getMember(); //작가 소피스토리 세팅
        CompletedBooktalk completedBooktalk = CompletedBooktalk.builder() // 완료된 북토크로 이동
            .title(booktalk.getTitle())
            .bookName(booktalk.getBook().getTitle())
            .authorName(booktalk.getMember().getName())
            .booktalkDate(booktalk.getEndDate())
            .placeName(booktalk.getPlace().getName())
            .bookCategory(booktalk.getBookCategory())
            .build();
        completedBooktalkRepository.save(completedBooktalk);
        member.getCompletedBookTalkList().add(completedBooktalk);
        completedBooktalk.setMember(member);
        booktalkRepository.delete(booktalk);
        return completedBooktalk;
    }
}
