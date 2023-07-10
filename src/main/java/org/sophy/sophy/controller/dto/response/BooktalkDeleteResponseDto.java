package org.sophy.sophy.controller.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BooktalkDeleteResponseDto {
    private Long booktalkId;

    public static BooktalkDeleteResponseDto of(Long booktalkId) {
        return new BooktalkDeleteResponseDto(booktalkId);
    }
}
