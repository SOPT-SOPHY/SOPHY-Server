package org.sophy.sophy.domain.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkDeadlineUpcomingDto;
import org.sophy.sophy.domain.enumerate.City;

@Getter
@AllArgsConstructor
@Builder
public class HomeResponseDto {

    private String name;
    private Integer booktalkCount; //나의 예정된 북토크
    private Integer myCityBooktalkCount; //내 지역 북토크
    private Boolean isAuthor;
    private List<BooktalkDeadlineUpcomingDto> booktalkDeadlineUpcoming;
    private List<City> cityRanks;

}
