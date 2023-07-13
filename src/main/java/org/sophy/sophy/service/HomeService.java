package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.Place;
import org.sophy.sophy.domain.dto.HomeResponseDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkDeadlineUpcomingDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkResponseDto;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.NotFoundException;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.infrastructure.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final BooktalkService booktalkService;

    public HomeResponseDto getHome(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        Integer booktalkCount = member.getUserBookTalkList().size();
        Integer myCityBooktalkCount = getMyCityBooktalkCount(member.getMyCity());
        List<BooktalkDeadlineUpcomingDto> booktalkDeadlineUpcoming = booktalkService.getBooktalkDeadlineUpcoming();

        return HomeResponseDto.builder()
                .booktalkCount(booktalkCount)
                .myCityBooktalkCount(myCityBooktalkCount)
                .booktalkDeadlineUpcoming(booktalkDeadlineUpcoming)
                .build();
    }

    public HomeResponseDto getGuestHome() {

        return HomeResponseDto.builder()
                .booktalkDeadlineUpcoming(booktalkService.getBooktalkDeadlineUpcoming())
                .build();
    }

    public Integer getMyCityBooktalkCount(City city) {
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
        return booktalkList.size();
    }
}
