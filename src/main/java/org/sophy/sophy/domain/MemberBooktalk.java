package org.sophy.sophy.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBooktalk extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_booktalk_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booktalk_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Booktalk booktalk;

    // 연관 관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        if(!member.getUserBookTalkList().contains(this)) {
            member.getUserBookTalkList().add(this);
        }
    }

    public void setBooktalk(Booktalk booktalk) {
        this.booktalk = booktalk;
        if (!booktalk.getParticipantList().contains(this)) {
            booktalk.getParticipantList().add(this);
        }
    }

    @Builder
    public MemberBooktalk(Member member, Booktalk booktalk, Boolean isConfirmed) {
        setMember(member);
        setBooktalk(booktalk);
    }
}
