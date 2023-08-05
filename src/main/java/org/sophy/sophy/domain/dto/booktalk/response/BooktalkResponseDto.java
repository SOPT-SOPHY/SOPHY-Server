package org.sophy.sophy.domain.dto.booktalk.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.sophy.sophy.domain.enumerate.PreliminaryInfo;

@Data
@AllArgsConstructor
public class BooktalkResponseDto {

    private Long booktalkId;
    private PreliminaryInfo preliminaryInfo;
    private String title;
    private String author;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String place;
    private Integer participant;
    private Integer maximum;
    private String booktalkImageUrl;
}
