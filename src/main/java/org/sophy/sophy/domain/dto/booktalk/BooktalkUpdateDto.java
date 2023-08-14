package org.sophy.sophy.domain.dto.booktalk;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.enumerate.BookCategory;
import org.sophy.sophy.domain.enumerate.PreliminaryInfo;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BooktalkUpdateDto {

    @NotNull(message = "유효하지 않은 공간 ID입니다.")
    @Schema(description = "장소 Id", example = "1")
    private Long placeId;
    private String booktalkImageUrl;
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
    @Schema(description = "북토크 시작시간", example = "2023-08-12 15:00:00")
    private LocalDateTime startDate; //TODO 시작 시간은 오늘날짜 이전은 안되도록?
    @NotNull(message = "유효하지 않은 종료 날짜입니다.")
    @Schema(description = "북토크 종료시간", example = "2023-08-12 17:00:00")
    private LocalDateTime endDate;
    @NotNull(message = "유효하지 않은 북토크 참가 인원입니다.")
    @Schema(description = "북토크 참가 인원", example = "8")
    private Integer participant;
    @NotNull(message = "유효하지 않은 북토크 참가비입니다.")
    @Schema(description = "북토크 참가 비", example = "1000")
    private Integer participationFee;
    @NotNull(message = "유효하지 않은 북토크 사전 준비 사항입니다.")
    @Schema(description = "북토크 사전 준비 사항", example = "PRE_READING")
    private PreliminaryInfo preliminaryInfo;
    @NotBlank(message = "유효하지 않은 북토크 상세 설명입니다.")
    @Schema(description = "북토크 상세 설명", example = "밖에 비온다 주륵주륵")
    private String description;
}
