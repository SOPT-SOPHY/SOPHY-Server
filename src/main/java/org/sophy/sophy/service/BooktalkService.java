package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.dto.booktalk.BooktalkUpdateDto;
import org.sophy.sophy.domain.dto.booktalk.request.BooktalkParticipationRequestDto;
import org.sophy.sophy.domain.dto.booktalk.request.BooktalkRequestDto;
import org.sophy.sophy.domain.dto.booktalk.response.*;
import org.sophy.sophy.domain.dto.CityRequestDto;
import org.sophy.sophy.domain.*;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.ForbiddenException;
import org.sophy.sophy.exception.model.NotFoundException;
import org.sophy.sophy.infrastructure.BooktalkRepository;
import org.sophy.sophy.infrastructure.MemberBooktalkRepository;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.infrastructure.PlaceRepository;
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

    @Transactional
    public BooktalkCreateResponseDto createBooktalk(BooktalkRequestDto booktalkRequestDto) {
        Place place = getPlaceById(booktalkRequestDto.getPlaceId());
        Member member = getMemberById(booktalkRequestDto.getMemberId());
        if (!member.getIsAuthor()) {
            throw new ForbiddenException(ErrorStatus.FORBIDDEN_USER_EXCEPTION, ErrorStatus.FORBIDDEN_USER_EXCEPTION.getMessage());
        }
        Booktalk booktalk = booktalkRequestDto.toBooktalk(place, member);
        return BooktalkCreateResponseDto.of(booktalkRepository.save(booktalk));
    }

    @Transactional
    public BooktalkUpdateDto updateBooktalk(Long booktalkId, BooktalkUpdateDto booktalkUpdateDto) {
        Booktalk booktalk = getBooktalkById(booktalkId);
        booktalk.patchBooktalk(booktalkUpdateDto, getPlaceById(booktalkUpdateDto.getPlaceId()));
        return booktalkUpdateDto;
    }

    @Transactional
    public BooktalkDeleteResponseDto deleteBooktalk(Long booktalkId) { // 수정필요 -> 테이블 외래키 고려하여 관련된 엔티티 전부 삭제해야 함
        Booktalk booktalk = getBooktalkById(booktalkId);
        //TODO soft delete?
        //공간이 거절 됐거나 공간 매칭중일 때만 삭제가능
        booktalk.getPlace().deleteBooktalk(booktalk);
        booktalk.getMember().getAuthorProperty().deleteBooktalk(booktalk);
        booktalkRepository.deleteById(booktalkId);
        return BooktalkDeleteResponseDto.of(booktalkId);
    }

    public BooktalkDetailResponseDto getBooktalkDetail(Long booktalkId) {
        Booktalk booktalk = getBooktalkById(booktalkId);
        return BooktalkDetailResponseDto.of(booktalk);
    }

    @Transactional
    public void postBooktalkParticipation(BooktalkParticipationRequestDto booktalkParticipationRequestDto) {
        Member member = getMemberById(booktalkParticipationRequestDto.getMemberId());
        Booktalk booktalk = getBooktalkById(booktalkParticipationRequestDto.getBooktalkId());
        // 복합키?
        MemberBooktalk memberBooktalk = booktalkParticipationRequestDto.toMemberBooktalk(booktalk, member);
        memberBooktalkRepository.save(memberBooktalk);
    }

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

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }

    private Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_PLACE_EXCEPTION, ErrorStatus.NOT_FOUND_PLACE_EXCEPTION.getMessage()));
    }

    private Booktalk getBooktalkById(Long booktalkId) {
        return booktalkRepository.findById(booktalkId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_BOOKTALK_EXCEPTION, ErrorStatus.NOT_FOUND_BOOKTALK_EXCEPTION.getMessage()));
    }

    @Transactional
    public List<BooktalkResponseDto> getBooktalksByCity(CityRequestDto cityRequestDto) { //지역으로 북토크 조회
        City city = cityRequestDto.getCity();

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
}
