package org.sophy.sophy.domain.dto.booktalk.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.Place;
import org.sophy.sophy.domain.enumerate.BookCategory;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.domain.enumerate.PreliminaryInfo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BooktalkRequestDto {
    @NotNull
    @Schema(description = "장소 Id", example = "1")
    private Long placeId;

    private MultipartFile booktalkImage;
    @NotBlank
    @Schema(description = "북토크 이름", example = "소나기")
    private String title;
    @NotNull
    @Schema(description = "책 분야", example = "LITERATURE")
    private BookCategory bookCategory;
    @NotNull
    @Schema(description = "책 Id", example = "1")
    private Long bookId;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "북토크 시작시간", example = "2023-08-12 15:00:00")
    private LocalDateTime startDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "북토크 종료시간", example = "2023-08-12 17:00:00")
    private LocalDateTime endDate;
    @NotNull
    @Schema(description = "북토크 참가 인원", example = "8")
    private Integer participant;
    @NotNull
    @Schema(description = "북토크 참가 비", example = "1000")
    private Integer participationFee;
    @NotNull
    @Schema(description = "북토크 사전 준비 사항", example = "PRE_READING")
    private PreliminaryInfo preliminaryInfo;
    @NotBlank
    @Schema(description = "북토크 상세 설명", example = "밖에 비온다 주륵주륵")
    private String description;

    public Booktalk toBooktalk(Place place, Member member, String booktalkImageUrl) {
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
