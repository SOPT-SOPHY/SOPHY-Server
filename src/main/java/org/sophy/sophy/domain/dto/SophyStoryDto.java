package org.sophy.sophy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class SophyStoryDto {
    private String title;
    private String bookName;
    private String authorName;
    private LocalDate booktalkDate;
    private String placeName;
}
