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
@NoArgsConstructor
public class Booktalk extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booktalk_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Place place;

    @Column(nullable = false)
    private String title;

    private String booktalkImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Member member;

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
            this.place.getBooktalkList().remove(this);
        }

        this.place = place;

        if (!place.getBooktalkList().contains(this)) {
            place.getBooktalkList().add(this);
        }
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getAuthor().getMyBookTalkList().remove(this);
        }
        this.member = member;
        if (!member.getAuthor().getMyBookTalkList().contains(this)) {
            member.getAuthor().getMyBookTalkList().add(this);
        }
    }

    @Builder
    public Booktalk(Place place, String title, String booktalkImageUrl, Member member, BookCategory bookCategory, LocalDateTime startDate, LocalDateTime endDate, Integer maximum, Integer participationFee, PreliminaryInfo preliminaryInfo, String description, BooktalkStatus booktalkStatus) {
        setPlace(place);
        this.title = title;
        this.booktalkImageUrl = booktalkImageUrl;
        this.member = member;
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