package org.sophy.sophy.controller.dto.request;

import lombok.Getter;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.MemberBooktalk;

import javax.validation.constraints.NotNull;

@Getter
public class BooktalkParticipationRequestDto {
    @NotNull
    private Long booktalkId;
    @NotNull
    private Long memberId;

    public MemberBooktalk toMemberBooktalk(Booktalk booktalk, Member member) {
        return MemberBooktalk.builder()
                .booktalk(booktalk)
                .member(member)
                .build();
    }
}
