package org.sophy.sophy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MyPageDto {
    private String name;
    private Booktalk imminentBooktalk;
    private Integer bookCount;
    private Integer bookTalkCount;
    private Integer matchingBookTalkCount;
    private Integer recruitBookTalkCount;
}
