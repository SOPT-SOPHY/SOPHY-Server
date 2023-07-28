package org.sophy.sophy.domain.dto.booktalk.request;

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
    private Long memberId;
    @NotNull
    private Long placeId;

    private MultipartFile booktalkImage;
    @NotBlank
    private String title;
    @NotNull
    private BookCategory bookCategory;
    @NotNull
    private Long bookId;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;
    @NotNull
    private Integer participant;
    @NotNull
    private Integer participationFee;
    @NotNull
    private PreliminaryInfo preliminaryInfo;
    @NotBlank
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
