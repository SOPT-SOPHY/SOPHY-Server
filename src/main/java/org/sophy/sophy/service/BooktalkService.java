package org.sophy.sophy.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.CompletedBooktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.MemberBooktalk;
import org.sophy.sophy.domain.Place;
import org.sophy.sophy.domain.dto.booktalk.BooktalkUpdateDto;
import org.sophy.sophy.domain.dto.booktalk.request.BooktalkParticipationRequestDto;
import org.sophy.sophy.domain.dto.booktalk.request.BooktalkRequestDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkCreateResponseDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkDeadlineUpcomingDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkDeleteResponseDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkDetailResponseDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkResponseDto;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.DuplParticipationException;
import org.sophy.sophy.exception.model.ForbiddenException;
import org.sophy.sophy.exception.model.OverMaxParticipationException;
import org.sophy.sophy.external.client.aws.S3Service;
import org.sophy.sophy.infrastructure.BooktalkRepository;
import org.sophy.sophy.infrastructure.CompletedBooktalkRepository;
import org.sophy.sophy.infrastructure.MemberBooktalkRepository;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.infrastructure.PlaceRepository;
import org.sophy.sophy.infrastructure.query.BooktalkQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BooktalkService {

    private final BooktalkRepository booktalkRepository;
    private final BooktalkQueryRepository booktalkQueryRepository;
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
        Member member = memberRepository.getMemberByEmail(email); //참가 신청 유저
        Booktalk booktalk = booktalkRepository.getBooktalkById(
            booktalkParticipationRequestDto.getBooktalkId()); //참가하고자 하는 북토크
        //북토크 현재 인원이 최대인원을 넘지 않았는지 체크하는 메서드
        if (booktalk.getMaximum() <= booktalk.getParticipantNum()) {
            throw new OverMaxParticipationException(ErrorStatus.OVER_MAX_PARTICIPATION_EXCEPTION,
                ErrorStatus.OVER_MAX_PARTICIPATION_EXCEPTION.getMessage());
        }
        List<Long> participantIds = booktalk.getParticipantList().stream()
            .map(b -> b.getMember().getId())
            .collect(Collectors.toList());
        if (participantIds.contains(member.getId())) {
            throw new DuplParticipationException(ErrorStatus.DUPL_PARTICIPATION_EXCEPTION,
                ErrorStatus.DUPL_PARTICIPATION_EXCEPTION.getMessage());
        }
        // 복합키?
        memberBooktalkRepository.save(
            booktalkParticipationRequestDto.toMemberBooktalk(booktalk, member));
    }

    // 마감임박 북토크 조회
    public List<BooktalkDeadlineUpcomingDto> getBooktalkDeadlineUpcoming() {
        return booktalkRepository.findAllByBooktalkStatusOrderByEndDate(BooktalkStatus.RECRUITING)
            .stream().map(BooktalkDeadlineUpcomingDto::of).collect(Collectors.toList());
    }

    //지역으로 북토크 조회
    @Transactional
    public List<BooktalkResponseDto> getBooktalksByCity(City city) {

        List<BooktalkResponseDto> booktalkList;
        /*
        @Query를 통해 Dto로 직접 조회 및 정렬까지 구현 가능할 듯
        Dto로 직접 조회할 땐 페이징이 불가능 한 문제가 생길 수 있지만 여기선 ToMany관계가 없어서 가능할 듯
         */
        if (city.equals(City.UIJEONGBU_SI)) { //모집중인 북토크만 조회
            booktalkList = booktalkQueryRepository.findBooktalkResponseDto(
                BooktalkStatus.RECRUITING);
        } else {
            booktalkList = booktalkQueryRepository.findBooktalkResponseDto(city,
                BooktalkStatus.RECRUITING);
        }

        return booktalkList;
    }

    @Transactional
    public CompletedBooktalk completeBooktalk(Long booktalkId) { //작가가 자신의 북토크를 완료상태로 변경하는 메서드
        Booktalk booktalk = booktalkRepository.getBooktalkById(booktalkId);
        return changeBooktalkToComplete(booktalk);
    }

    //북토크 상태를 완료로 변경하는 메서드
    public CompletedBooktalk changeBooktalkToComplete(Booktalk booktalk) {
        booktalk.setBooktalkStatus(BooktalkStatus.COMPLETED);
        for (MemberBooktalk memberBooktalk : booktalk.getParticipantList()) { //참가 인원들 소피스토리 세팅
            Member member = memberBooktalk.getMember();
            completedBooktalkSetting(booktalk, member);
            member.minusUserBooktalk(); //예정된 북토크 수 감소
        }
        Member member = booktalk.getMember(); //작가 소피스토리 세팅
        CompletedBooktalk completedBooktalk = completedBooktalkSetting(booktalk, member);
        member.getAuthorProperty().minusMyBookTalkSize(); //개최한 북토크 수 감소
        booktalkRepository.delete(booktalk);
        return completedBooktalk;
    }

    //완료된 북토크 연관관계 설정
    private CompletedBooktalk completedBooktalkSetting(Booktalk booktalk, Member member) {
        CompletedBooktalk completedBooktalk = CompletedBooktalk.toBuild(booktalk);
        completedBooktalkRepository.save(completedBooktalk);
        completedBooktalk.setMember(member); //연관관계 설정
        return completedBooktalk;
    }
}
