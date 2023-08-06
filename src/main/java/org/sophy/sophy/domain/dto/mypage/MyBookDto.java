package org.sophy.sophy.domain.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.enumerate.BookCategory;

@Getter
@AllArgsConstructor
public class MyBookDto {

    private String title;
    private BookCategory bookCategory;
    private Integer booktalkOpenCount;
    private Boolean isRegistration;
    private String bookImageUrl;
}
