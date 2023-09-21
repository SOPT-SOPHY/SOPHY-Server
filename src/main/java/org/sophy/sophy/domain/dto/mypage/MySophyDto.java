package org.sophy.sophy.domain.dto.mypage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MySophyDto {

    private String name;
    private String email;
    private Integer myBookCount;

}
