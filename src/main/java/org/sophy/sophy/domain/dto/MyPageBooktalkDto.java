package org.sophy.sophy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sophy.sophy.domain.BooktalkStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class MyPageBooktalkDto {
    private Long booktalkId;
    private String booktalkImageUrl;
    private String title;
    private String author;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String place;
    private Integer participant;
    private Integer maximum;
    private BooktalkStatus booktalkStatus;
}
