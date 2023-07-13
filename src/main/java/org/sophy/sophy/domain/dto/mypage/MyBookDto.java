package org.sophy.sophy.domain.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sophy.sophy.domain.enumerate.BookCategory;

@Getter
@AllArgsConstructor
@Builder
public class MyBookDto {
    private String title;
    private BookCategory bookCategory;
    private Integer booktalkOpenCount;
    private Boolean isRegistration;
}
