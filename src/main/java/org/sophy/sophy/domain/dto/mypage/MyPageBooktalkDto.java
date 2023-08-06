package org.sophy.sophy.domain.dto.mypage;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;

@Getter
@AllArgsConstructor
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
