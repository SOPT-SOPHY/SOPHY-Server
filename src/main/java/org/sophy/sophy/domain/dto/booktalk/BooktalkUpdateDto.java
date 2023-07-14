package org.sophy.sophy.domain.dto.booktalk;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.enumerate.BookCategory;
import org.sophy.sophy.domain.enumerate.PreliminaryInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BooktalkUpdateDto {
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
}
