package org.sophy.sophy.domain.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sophy.sophy.domain.enumerate.BookCategory;

@Getter
@Builder
@AllArgsConstructor
public class SophyStoryDto {

    private String title;
    private String bookName;
    private String authorName;
    private LocalDateTime booktalkDate;
    private String placeName;
    private BookCategory bookCategory;
}
