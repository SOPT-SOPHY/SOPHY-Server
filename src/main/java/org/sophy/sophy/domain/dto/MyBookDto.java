package org.sophy.sophy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sophy.sophy.domain.BookCategory;

@Getter
@AllArgsConstructor
@Builder
public class MyBookDto {
    private String title;
    private BookCategory bookCategory;
    private Integer booktalkOpenCount;
    private Boolean isRegistration;
}
