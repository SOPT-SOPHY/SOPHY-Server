package org.sophy.sophy.domain.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class MyPageDto {
    private String name;
    private Integer expectedBookTalkCount; // 개최 예정 북토크
    private Integer waitingBookTalkCount; // 참여 예정 북토크
    private Integer completeBookTalkCount; // 참여 완료 북토크
    private List<MyPageBooktalkDto> myPageBooktalkDtos; //예정된 북토크
    private List<MyBookDto> myBookDtos; // 내 도서 관리
}
