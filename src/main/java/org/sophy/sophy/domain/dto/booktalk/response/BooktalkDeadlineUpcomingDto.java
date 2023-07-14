package org.sophy.sophy.domain.dto.booktalk.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.Booktalk;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BooktalkDeadlineUpcomingDto {
    private Long booktalkId;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String PlaceName;
    private String PlaceAddress;

    public static BooktalkDeadlineUpcomingDto of(Booktalk booktalk) {
        return new BooktalkDeadlineUpcomingDto(
                booktalk.getId(),
                booktalk.getTitle(),
                booktalk.getStartDate(),
                booktalk.getEndDate(),
                booktalk.getPlace().getName(),
                booktalk.getPlace().getAddress()
        );
    }
}
