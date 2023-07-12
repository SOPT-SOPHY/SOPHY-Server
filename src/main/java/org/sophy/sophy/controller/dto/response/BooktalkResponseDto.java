package org.sophy.sophy.controller.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.Booktalk;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BooktalkResponseDto {
    private Long booktalkId;
    private Integer preliminaryInfo;
    private String title;
    private String author;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String place;
    private Integer participant;
    private Integer maximum;
    private String booktalkImageUrl;

    public static BooktalkResponseDto of(Booktalk booktalk) {
        return new BooktalkResponseDto(
                booktalk.getId(),
                booktalk.getPreliminaryInfo().ordinal(),
                booktalk.getTitle(),
                booktalk.getMember().getName(),
                booktalk.getStartDate(),
                booktalk.getEndDate(),
                booktalk.getPlace().getName(),
                booktalk.getParticipantList().size(),
                booktalk.getMaximum(),
                booktalk.getBooktalkImageUrl());
    }
}
