package org.sophy.sophy.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
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

    private Boolean isConfirmed;

    // 연관 관계 편의 메서드
    public void setMember(Member member) {
        if(this.member != null) {
            this.member.getUserBookTalkList().remove(this); //이거 PATCH될 가능성이 있나?
        }
        this.member = member;
        if(!member.getUserBookTalkList().contains(this)) {
            member.getUserBookTalkList().add(this);
        }
    }

    public void setBooktalk(Booktalk booktalk) {
        if (this.booktalk != null) {
            this.booktalk.getParticipantList().remove(this);
        }
        this.booktalk = booktalk;
        if (!booktalk.getParticipantList().contains(this)) {
            booktalk.getParticipantList().add(this);
        }
    }

    @Builder
    public MemberBooktalk(Member member, Booktalk booktalk, Boolean isConfirmed) {
        this.member = member;
        setBooktalk(booktalk);
        this.isConfirmed = isConfirmed;
    }
}
