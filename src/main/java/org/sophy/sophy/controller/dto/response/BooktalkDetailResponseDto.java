package org.sophy.sophy.controller.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.BookCategory;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.PreliminaryInfo;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BooktalkDetailResponseDto {
    private String booktalkImageUrl;
    private String title;
    private String Author;
    private BookCategory bookCategory;
    private String book; //TODO 추후 연결
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer participant;
    private Integer participationFee;
    private PreliminaryInfo preliminaryInfo;
    private String description;
    private String PlaceName;
    private String PlaceAddress;

    public static BooktalkDetailResponseDto of(Booktalk booktalk) {
        return new BooktalkDetailResponseDto(
                booktalk.getBooktalkImageUrl(),
                booktalk.getTitle(),
                booktalk.getMember().getName(),
                booktalk.getBookCategory(),
                "책이름", //TODO 추후 연결
                booktalk.getStartDate(),
                booktalk.getEndDate(),
                booktalk.getParticipantList().size(),
                booktalk.getParticipationFee(),
                booktalk.getPreliminaryInfo(),
                booktalk.getDescription(),
                booktalk.getPlace().getName(),
                booktalk.getPlace().getAddress()
        );
    }
}
