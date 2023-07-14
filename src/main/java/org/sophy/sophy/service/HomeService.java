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
import org.sophy.sophy.infrastructure.BooktalkRepository;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.infrastructure.PlaceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final BooktalkService booktalkService;
    private final BooktalkRepository booktalkRepository;

    public HomeResponseDto getHome(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
        Integer booktalkCount = member.getUserBookTalkList().size();
        List<BooktalkDeadlineUpcomingDto> booktalkDeadlineUpcoming = booktalkService.getBooktalkDeadlineUpcoming();

        if (member.getIsAuthor()){ //작가냐 아니냐에 따라 홈 화면 분리
            List<City> cityRank = getCityRank();
            return HomeResponseDto.builder()
                    .name(member.getName())
                    .isAuthor(true)
                    .booktalkCount(booktalkCount)
                    .cityRanks(cityRank)
                    .booktalkDeadlineUpcoming(booktalkDeadlineUpcoming)
                    .build();
        } else {
            Integer myCityBooktalkCount = member.getMyCity()!=null ? getMyCityBooktalkCount(member.getMyCity()) : null;
            return HomeResponseDto.builder()
                    .name(member.getName())
                    .isAuthor(false)
                    .booktalkCount(booktalkCount)
                    .myCityBooktalkCount(myCityBooktalkCount)
                    .booktalkDeadlineUpcoming(booktalkDeadlineUpcoming)
                    .build();
        }
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

    public List<City> getCityRank() {
        Map<City, Integer> rank = new HashMap<City, Integer>();
        for (City city : City.values()) {
            Integer count = booktalkRepository.countAllByCityAndCreateAtBetween(city
                    , LocalDateTime.of(LocalDate.now().minusDays(30)
                            , LocalTime.of(0,0,0))
                    , LocalDateTime.of(LocalDate.now()
                            , LocalTime.of(23, 59, 59)));
            rank.put(city, count);
        }
        List<Map.Entry<City,Integer>> cityLists = rank.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());
        System.out.println(cityLists);

        List<City> result = new ArrayList<>();
        for(Map.Entry<City, Integer> entry : cityLists.subList(0, 3)){
            result.add(entry.getKey());
        }

        return result;
    }
}
