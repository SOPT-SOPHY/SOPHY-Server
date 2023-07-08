package org.sophy.sophy.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "BOOKTALK")
@NoArgsConstructor
public class Booktalk extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Place place;

    @Column(nullable = false)
    private String title;

    private String booktalkImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Author author;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookCategory bookCategory;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Integer maximum;

    @Column(nullable = false)
    private Integer participationFee;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PreliminaryInfo preliminaryInfo;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BooktalkStatus booktalkStatus;

    @OneToMany(mappedBy = "booktalk")
    private List<MemberBooktalk> participantList = new ArrayList<>();

    // 연관 관계 편의 메서드
    public void setPlace(Place place) {
        if (this.place != null) {
            this.place.getPendingBooktalkList().remove(this);
        }

        this.place = place;

        if (!place.getPendingBooktalkList().contains(this)) {
            place.getPendingBooktalkList().add(this);
        }
    }

    public void setAuthor(Author author) {
        if (this.author != null) {
            this.author.getMyBookTalkList().remove(this);
        }
        this.author = author;
        if (!author.getMyBookTalkList().contains(this)) {
            author.getMyBookTalkList().add(this);
        }
    }

    @Builder
    public Booktalk(Place place, String title, String booktalkImageUrl, Author author, BookCategory bookCategory, LocalDateTime startDate, LocalDateTime endDate, Integer maximum, Integer participationFee, PreliminaryInfo preliminaryInfo, String description, BooktalkStatus booktalkStatus) {
        setPlace(place);
        this.title = title;
        this.booktalkImageUrl = booktalkImageUrl;
        this.author = author;
        this.bookCategory = bookCategory;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maximum = maximum;
        this.participationFee = participationFee;
        this.preliminaryInfo = preliminaryInfo;
        this.description = description;
        this.booktalkStatus = booktalkStatus;
        this.participantList = new ArrayList<>();
    }
}