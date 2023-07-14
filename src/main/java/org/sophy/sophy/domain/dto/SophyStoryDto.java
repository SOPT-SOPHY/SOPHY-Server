package org.sophy.sophy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SophyStoryDto {
    private String title;
    private String bookName;
    private String authorName;
    private LocalDateTime booktalkDate;
    private String placeName;
}
