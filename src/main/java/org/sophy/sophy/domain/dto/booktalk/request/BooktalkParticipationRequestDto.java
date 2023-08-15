package org.sophy.sophy.domain.dto.booktalk.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.MemberBooktalk;

@Getter
public class BooktalkParticipationRequestDto {

    @NotNull(message = "유효하지 않은 북토크 ID입니다.")
    @Schema(description = "북토크 Id", example = "5")
    private Long booktalkId;

    public MemberBooktalk toMemberBooktalk(Booktalk booktalk, Member member) {
        return MemberBooktalk.builder()
            .booktalk(booktalk)
            .member(member)
            .build();
    }
}
