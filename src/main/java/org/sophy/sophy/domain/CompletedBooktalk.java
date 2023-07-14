package org.sophy.sophy.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompletedBooktalk extends AuditingTimeEntity{
    @Id
    @GeneratedValue
    @Column(name = "completed_booktalk_id")
    private Long id;

    private String title;
    private String bookName;
    private String authorName;
    private LocalDateTime booktalkDate;
    private String placeName;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public CompletedBooktalk(String title, String bookName, String authorName, LocalDateTime booktalkDate, String placeName) {
        this.title = title;
        this.bookName = bookName;
        this.authorName = authorName;
        this.booktalkDate = booktalkDate;
        this.placeName = placeName;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}