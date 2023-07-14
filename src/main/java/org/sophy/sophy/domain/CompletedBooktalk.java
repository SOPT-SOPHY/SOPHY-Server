package org.sophy.sophy.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompletedBooktalk {
    @Id
    @GeneratedValue
    @Column(name = "completed_booktalk_id")
    private Long id;

    private String title;
    private String bookName;
    private String authorName;
    private LocalDate booktalkDate;
    private String placeName;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
