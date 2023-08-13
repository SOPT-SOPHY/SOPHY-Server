package org.sophy.sophy.service.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.dto.HomeResponseDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkDeadlineUpcomingDto;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.infrastructure.BooktalkRepository;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final MemberRepository memberRepository;
    private final BooktalkService booktalkService;
    private final BooktalkRepository booktalkRepository;

    public HomeResponseDto getHome(String email) {
        Member member = memberRepository.getMemberByEmail(email);
        Integer booktalkCount = member.getUserBookTalkSize();
        List<BooktalkDeadlineUpcomingDto> booktalkDeadlineUpcoming = booktalkService.getBooktalkDeadlineUpcoming();

        if (member.getAuthority().equals(Authority.AUTHOR)) { //작가냐 아니냐에 따라 홈 화면 분리
            List<City> cityRank = getCityRank();
            return HomeResponseDto.builder()
                .name(member.getName())
                .isAuthor(true)
                .booktalkCount(booktalkCount)
                .cityRanks(cityRank)
                .booktalkDeadlineUpcoming(booktalkDeadlineUpcoming)
                .build();
        } else {
            Integer myCityBooktalkCount =
                member.getMyCity() != null ? getMyCityBooktalkCount(member.getMyCity()) : null;
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

        if (city.equals(City.UIJEONGBU_SI)) {
            return booktalkRepository.countAllByBooktalkStatus(BooktalkStatus.RECRUITING);
        } else {
            return booktalkRepository.countAllByCityAndBooktalkStatus(city,
                BooktalkStatus.RECRUITING);
        }
    }

    public List<City> getCityRank() {
        Map<City, Integer> rank = new HashMap<>();
        for (City city : City.values()) {
            Integer count = booktalkRepository.countAllByCityAndCreateAtBetween(city
                , LocalDateTime.of(LocalDate.now().minusDays(30)
                    , LocalTime.of(0, 0, 0))
                , LocalDateTime.of(LocalDate.now()
                    , LocalTime.of(23, 59, 59)));
            rank.put(city, count);
        }
        List<Map.Entry<City, Integer>> cityLists = rank.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toList());

        List<City> result = new ArrayList<>();
        for (Map.Entry<City, Integer> entry : cityLists.subList(0, 3)) {
            result.add(entry.getKey());
        }

        return result;
    }
}
