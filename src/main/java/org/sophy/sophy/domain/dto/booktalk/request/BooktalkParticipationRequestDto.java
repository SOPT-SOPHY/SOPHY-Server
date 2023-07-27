package org.sophy.sophy.domain.dto.booktalk.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.MemberBooktalk;

import javax.validation.constraints.NotNull;

@Getter
public class BooktalkParticipationRequestDto {
    @NotNull
    @Schema(description = "북토크 Id", example = "1")
    private Long booktalkId;

    public MemberBooktalk toMemberBooktalk(Booktalk booktalk, Member member) {
        return MemberBooktalk.builder()
                .booktalk(booktalk)
                .member(member)
                .build();
    }
}
