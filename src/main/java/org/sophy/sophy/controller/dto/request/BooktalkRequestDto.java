package org.sophy.sophy.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sophy.sophy.domain.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BooktalkRequestDto {
    @NotNull
    private Long memberId;
    @NotNull
    private Long placeId;

    private String booktalkImageUrl;
    @NotBlank
    private String title;
    @NotNull
    private BookCategory bookCategory;
    @NotNull
    private Long bookId; //TODO 추후 연결
    @NotNull
    private LocalDateTime startDate; //TODO 시작 시간은 오늘날짜 이전은 안되도록?
    @NotNull
    private LocalDateTime endDate;
    @NotNull
    private Integer participant;
    @NotNull
    private Integer participationFee;
    @NotNull
    private PreliminaryInfo preliminaryInfo;
    @NotBlank
    private String description;

    public Booktalk toBooktalk(Place place, Member member) {
        return Booktalk.builder()
                .title(title)
                .booktalkImageUrl(booktalkImageUrl)
                .bookCategory(bookCategory)
                .startDate(startDate)
                .endDate(endDate)
                .maximum(participant)
                .participationFee(participationFee)
                .preliminaryInfo(preliminaryInfo)
                .description(description)
                .booktalkStatus(BooktalkStatus.APPLYING)
                .member(member)
                .place(place)
                .build();
    }

}
