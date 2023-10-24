package org.sophy.sophy.domain.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PreliminaryInfo {
    //미리 읽어와주세요 / 발췌문을 드려요 / 편하게와주세요 / 질문을 준비해주세요
    PRE_READING("미리 읽어와주세요"),
    PROVIDE_EXCERPT("발췌문을 드려요"),
    COMFORTABLE_COMING("편하게 와주세요"),
    PREPARE_QUESTION("질문을 준비해주세요")
    ;

    private final String description;
}
