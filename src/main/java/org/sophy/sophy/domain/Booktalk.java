package org.sophy.sophy.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.sophy.sophy.domain.dto.booktalk.BooktalkUpdateDto;
import org.sophy.sophy.domain.enumerate.BookCategory;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.domain.enumerate.PreliminaryInfo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sophy.sophy.domain.other.AuditingEntity;
import org.sophy.sophy.domain.other.ScheduledBooktalk;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE booktalk SET deleted = true WHERE booktalk_id=?")
@Where(clause = "deleted=false")
public class Booktalk extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booktalk_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private City city;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String booktalkImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //작가 = 북토크당 1명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_property_id")
    private AuthorProperty authorProperty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_property_id")
    private OperatorProperty operatorProperty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookCategory bookCategory;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private Integer participantNum;

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

    @OneToMany(mappedBy = "booktalk", cascade = CascadeType.ALL, orphanRemoval = true)
    //개념적으로 부모를 제거하면 자식은 고아가 됨 -> 고아 객체 제거 기능을 활성화하면 자식도 함께 제거
    private List<MemberBooktalk> participantList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduled_booktalk_id")
    private ScheduledBooktalk scheduledBooktalk;

    private final Boolean deleted = Boolean.FALSE;

    public void plusParticipant() {
        this.participantNum += 1;
    }

    public void setBooktalkStatus(BooktalkStatus booktalkStatus) {
        this.booktalkStatus = booktalkStatus;
    }

    // 연관 관계 편의 메서드
    public void setPlace(Place place) {
        if (this.place != null) {
            this.place.getBooktalkList().remove(this);
        }

        this.place = place;
        this.city = place.getCity();

        if (!place.getBooktalkList().contains(this)) {
            place.getBooktalkList().add(this);
        }
    }

    public void setAuthor(Member member) {
        if (this.member != null) {
            this.member.getAuthorProperty().getMyBookTalkList().remove(this);
        }
        this.member = member;
        this.authorProperty = member.getAuthorProperty();
        member.getAuthorProperty().getMyBookTalkList().add(this);
        member.getAuthorProperty().plusMyBookTalkSize();
    }

    @Builder
    public Booktalk(Place place, String title, String booktalkImageUrl, Book book, Member member,
        BookCategory bookCategory, LocalDateTime startDate, LocalDateTime endDate, Integer maximum,
        Integer participationFee, PreliminaryInfo preliminaryInfo, String description,
        BooktalkStatus booktalkStatus) {
        setPlace(place);
        this.title = title;
        this.booktalkImageUrl = booktalkImageUrl;
        this.book = book;
        setAuthor(member);
        this.bookCategory = bookCategory;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participantNum = 0;
        this.maximum = maximum;
        this.participationFee = participationFee;
        this.preliminaryInfo = preliminaryInfo;
        this.description = description;
        this.booktalkStatus = booktalkStatus;
        this.participantList = new ArrayList<>();
    }

    public void patchBooktalk(BooktalkUpdateDto booktalkUpdateDto, Place place) {
        setPlace(place);
        this.title = booktalkUpdateDto.getTitle();
        this.booktalkImageUrl = booktalkUpdateDto.getBooktalkImageUrl();
        this.bookCategory = booktalkUpdateDto.getBookCategory();
        this.startDate = booktalkUpdateDto.getStartDate();
        this.endDate = booktalkUpdateDto.getEndDate();
        this.maximum = booktalkUpdateDto.getParticipant();
        this.participationFee = booktalkUpdateDto.getParticipationFee();
        this.preliminaryInfo = booktalkUpdateDto.getPreliminaryInfo();
        this.description = booktalkUpdateDto.getDescription();
    }
}