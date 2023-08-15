package org.sophy.sophy.domain.dto.booktalk.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BooktalkRequestDto {

    @NotNull(message = "유효하지 않은 공간 ID입니다.")
    @Schema(description = "장소 Id", example = "1")
    private Long placeId;

    private MultipartFile booktalkImage;
    @NotBlank(message = "유효하지 않은 북토크 제목입니다.")
    @Schema(description = "북토크 이름", example = "소나기")
    private String title;
    @NotNull(message = "유효하지 않은 책 분야입니다.")
    @Schema(description = "책 분야", example = "LITERATURE")
    private BookCategory bookCategory;
    @NotNull(message = "유효하지 않은 책 ID입니다.")
    @Schema(description = "책 Id", example = "1")
    private Long bookId;
    @NotNull(message = "유효하지 않은 시작 날짜입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;
    @NotNull(message = "유효하지 않은 종료 날짜입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;
    @NotNull(message = "유효하지 않은 북토크 참가 인원입니다.")
    @Schema(description = "북토크 참가 인원", example = "8")
    private Integer participant;
    @NotNull(message = "유효하지 않은 북토크 참가비입니다.")
    @Schema(description = "북토크 참가비", example = "1000")
    private Integer participationFee;
    @NotNull(message = "유효하지 않은 북토크 사전 준비 사항입니다.")
    @Schema(description = "북토크 사전 준비 사항", example = "PRE_READING")
    private PreliminaryInfo preliminaryInfo;
    @NotBlank(message = "유효하지 않은 북토크 상세 설명입니다.")
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
