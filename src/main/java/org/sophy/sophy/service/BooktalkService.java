package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.BooktalkUpdateDto;
import org.sophy.sophy.controller.dto.request.BooktalkRequestDto;
import org.sophy.sophy.controller.dto.request.CityRequestDto;
import org.sophy.sophy.controller.dto.response.*;
import org.sophy.sophy.domain.*;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.ForbiddenException;
import org.sophy.sophy.exception.model.NotFoundException;
import org.sophy.sophy.infrastructure.BooktalkRepository;
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
    public BooktalkDeleteResponseDto deleteBooktalk(Long booktalkId) {
        Booktalk booktalk = getBooktalkById(booktalkId);
        //TODO soft delete?
        //공간이 거절 됐거나 공간 매칭중일 때만 삭제가능
        booktalk.getPlace().deleteBooktalk(booktalk);
        booktalk.getAuthor().getAuthorProperty().deleteBooktalk(booktalk);
        booktalkRepository.deleteById(booktalkId);
        return BooktalkDeleteResponseDto.of(booktalkId);
    }

    public BooktalkDetailResponseDto getBooktalkDetail(Long booktalkId) {
        Booktalk booktalk = getBooktalkById(booktalkId);
        return BooktalkDetailResponseDto.of(booktalk);
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
    public List<BooktalkResponseDto> getBooktalksByCity(CityRequestDto cityRequestDto) {
        City city = cityRequestDto.getCity();
        List<Place> placeList;

        if (city.equals(City.UIJEONGBU_SI)) {
            placeList = placeRepository.findAll();
        } else {
            placeList = placeRepository.findAllByCity(city);
        }

        List<BooktalkResponseDto> booktalkList = new ArrayList<>();
        placeList.forEach(place -> {
            place.getBooktalkList().forEach(booktalk -> {
                        // 모집중인 북토크만 추가
                        if (booktalk.getBooktalkStatus() == BooktalkStatus.RECRUITING) {
                            booktalkList.add(BooktalkResponseDto.of(booktalk));
                        }
                    }
            );
        });

        // 마감 임박순으로 정렬
        booktalkList.sort(Comparator.comparing(BooktalkResponseDto::getEndDate));

        return booktalkList;
    }
}
