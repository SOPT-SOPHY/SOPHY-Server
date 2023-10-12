package org.sophy.sophy.domain.dto.booktalk.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.enumerate.BookCategory;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.domain.enumerate.PreliminaryInfo;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BooktalkDetailResponseDto {

    private String booktalkImageUrl;
    private String title;
    private String Author;
    private BookCategory bookCategory;
    private String book;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer participant;
    private Integer participationFee;
    private PreliminaryInfo preliminaryInfo;
    private String description;
    private String PlaceName;
    private String PlaceAddress;
    private Boolean isApply;
    private BooktalkStatus booktalkStatus;

    public static BooktalkDetailResponseDto of(Booktalk booktalk, Boolean isApply) {
        return new BooktalkDetailResponseDto(
            booktalk.getBooktalkImageUrl(),
            booktalk.getTitle(),
            booktalk.getMember().getName(),
            booktalk.getBookCategory(),
            booktalk.getBook().getTitle(),
            booktalk.getStartDate(),
            booktalk.getEndDate(),
            booktalk.getParticipantNum(),
            booktalk.getParticipationFee(),
            booktalk.getPreliminaryInfo(),
            booktalk.getDescription(),
            booktalk.getPlace().getName(),
            booktalk.getPlace().getAddress(),
            isApply,
            booktalk.getBooktalkStatus()
        );
    }
}
