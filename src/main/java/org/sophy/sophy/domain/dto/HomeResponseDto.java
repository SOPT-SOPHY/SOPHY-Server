package org.sophy.sophy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkDeadlineUpcomingDto;
import org.sophy.sophy.domain.enumerate.City;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class HomeResponseDto {
    private String name;
    private Integer booktalkCount;
    private Integer myCityBooktalkCount;
    private List<BooktalkDeadlineUpcomingDto> booktalkDeadlineUpcoming;
    private List<City> cityRanks;

}
