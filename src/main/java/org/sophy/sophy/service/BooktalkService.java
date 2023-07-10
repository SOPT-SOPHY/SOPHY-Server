package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.request.BooktalkRequestDto;
import org.sophy.sophy.controller.dto.BooktalkUpdateDto;
import org.sophy.sophy.controller.dto.response.BooktalkCreateResponseDto;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.Place;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.NotFoundException;
import org.sophy.sophy.infrastructure.BooktalkRepository;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.infrastructure.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BooktalkService {
    private final BooktalkRepository booktalkRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BooktalkCreateResponseDto createBooktalk(BooktalkRequestDto booktalkRequestDto) {
        Place place = getPlaceById(booktalkRequestDto.getPlaceId());
        //작가인지 확인할 필요가 있는지?
        Member member = getMemberById(booktalkRequestDto.getMemberId());
        Booktalk booktalk = booktalkRequestDto.toBooktalk(place, member);
        return BooktalkCreateResponseDto.of(booktalkRepository.save(booktalk));
    }

    @Transactional
    public BooktalkUpdateDto updateBooktalk(Long booktalkId, BooktalkUpdateDto booktalkUpdateDto) {
        Booktalk booktalk = booktalkRepository.findById(booktalkId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_BOOKTALK_EXCEPTION, ErrorStatus.NOT_FOUND_BOOKTALK_EXCEPTION.getMessage()));
        if (booktalkUpdateDto.getPlaceId() != booktalk.getPlace().getId()) {
            Place place = getPlaceById(booktalkUpdateDto.getPlaceId());
            booktalk.setPlace(place);
        }
        booktalk.patchBooktalk(booktalkUpdateDto);
        return booktalkUpdateDto;
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }

    private Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_PLACE_EXCEPTION, ErrorStatus.NOT_FOUND_PLACE_EXCEPTION.getMessage()));
    }
}
