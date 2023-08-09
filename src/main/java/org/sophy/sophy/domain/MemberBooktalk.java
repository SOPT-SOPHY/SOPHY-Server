package org.sophy.sophy.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import org.sophy.sophy.domain.other.AuditingEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBooktalk extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_booktalk_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booktalk_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Booktalk booktalk;

    // 연관 관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        if (!member.getUserBookTalkList().contains(this)) {
            // 시작날짜로 순으로 정렬해서 추가
            member.getUserBookTalkList().add(this);
            member.plusUserBooktalk();
        }
    }

    public void setBooktalk(Booktalk booktalk) {
        this.booktalk = booktalk;
        if (!booktalk.getParticipantList().contains(this)) {
            booktalk.getParticipantList().add(this);
            booktalk.plusParticipant();
        }
    }

    @Builder
    public MemberBooktalk(Member member, Booktalk booktalk) {
        //Member_Booltalk 관련 연관관계 변경
        setBooktalk(booktalk);
        setMember(member);
    }
}
