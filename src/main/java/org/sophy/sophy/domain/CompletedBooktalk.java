package org.sophy.sophy.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sophy.sophy.domain.enumerate.BookCategory;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompletedBooktalk extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "completed_booktalk_id")
    private Long id;

    private String title;
    private String bookName;
    private String authorName;
    private LocalDateTime booktalkDate;
    private String placeName;
    private BookCategory bookCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public CompletedBooktalk(String title, String bookName, String authorName,
        LocalDateTime booktalkDate, String placeName, BookCategory bookCategory) {
        this.title = title;
        this.bookName = bookName;
        this.authorName = authorName;
        this.booktalkDate = booktalkDate;
        this.placeName = placeName;
        this.bookCategory = bookCategory;
    }

    public static CompletedBooktalk toBuild(Booktalk booktalk) {
        return CompletedBooktalk.builder() // 완료된 북토크로 이동
            .title(booktalk.getTitle())
            .bookName(booktalk.getBook().getTitle())
            .authorName(booktalk.getMember().getName())
            .booktalkDate(booktalk.getEndDate())
            .placeName(booktalk.getPlace().getName())
            .bookCategory(booktalk.getBookCategory())
            .build();
    }

    public void setMember(Member member) {
        this.member = member;
        member.getCompletedBookTalkList().add(this);
    }
}
