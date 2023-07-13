package org.sophy.sophy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkDeadlineUpcomingDto;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class HomeResponseDto {
    private Integer booktalkCount;
    private Integer myCityBooktalkCount;
    private List<BooktalkDeadlineUpcomingDto> booktalkDeadlineUpcoming;

}
