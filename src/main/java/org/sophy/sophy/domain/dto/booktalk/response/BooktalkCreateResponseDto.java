package org.sophy.sophy.domain.dto.booktalk.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sophy.sophy.domain.Booktalk;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BooktalkCreateResponseDto {
    private Long booktalkId;
    private String title;

    public static BooktalkCreateResponseDto of(Booktalk booktalk) {
        return new BooktalkCreateResponseDto(booktalk.getId(), booktalk.getTitle());
    }
}
