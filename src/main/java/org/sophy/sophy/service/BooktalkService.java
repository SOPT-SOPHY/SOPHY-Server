package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.request.BooktalkRequestDto;
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
        Place place = placeRepository.findById(booktalkRequestDto.getPlaceId())
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_PLACE_EXCEPTION, ErrorStatus.NOT_FOUND_PLACE_EXCEPTION.getMessage()));
        //작가인지 확인할 필요가 있는지?
        Member member = memberRepository.findById(booktalkRequestDto.getMemberId())
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        Booktalk booktalk = booktalkRequestDto.toBooktalk(place, member);
        return BooktalkCreateResponseDto.of(booktalkRepository.save(booktalk));
    }
}
