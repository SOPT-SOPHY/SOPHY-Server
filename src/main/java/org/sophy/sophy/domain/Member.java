package org.sophy.sophy.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.domain.dto.mypage.MyInfoDto;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.domain.enumerate.City;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @NotNull
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phoneNum;

    private String gender;
    private String birth;

    private City myCity;

    private Boolean marketingAgree;

    @Column(nullable = false)
    private Boolean isAuthor;

    @Column(nullable = false)
    private Boolean isOperator;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private Integer waitingBookTalkCount; // 참여 예정 북토크
    private Integer completeBookTalkCount; // 참여 완료 북토크

    @OneToMany(mappedBy = "member")
    private List<MemberBooktalk> userBookTalkList;

    @OneToMany(mappedBy = "member")
    private List<CompletedBooktalk> completedBookTalkList;

    @OneToOne
    @JoinColumn(name = "author_property_id")
    private AuthorProperty authorProperty; //(개설한 북토크 리스트 + 나의 책 리스트 + 공간 매칭 중 북토크 수 + 청중 모집 중 북토크 수)

    @OneToOne
    @JoinColumn(name = "operator_property_id")
    private OperatorProperty operatorProperty;

    @Builder
    public Member(String name, String email, String password, String phoneNum, boolean marketingAgree, boolean isAuthor, boolean isOperator, Authority authority) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.marketingAgree = marketingAgree;
        this.isAuthor = isAuthor;
        this.isOperator = isOperator;
        this.authority = authority;
        this.userBookTalkList = new ArrayList<>();
        this.completedBookTalkList = new ArrayList<>();
    }

    public void setAuthorProperty(AuthorProperty authorProperty) {
        this.authorProperty = authorProperty;
    }
    public void setOperatorProperty(OperatorProperty operatorProperty) {
        this.operatorProperty = operatorProperty;
    }

    public void setWaitingBookTalkCount(int count) {
        this.waitingBookTalkCount = count;
    }

    public void setCompleteBookTalkCount(int count) {
        this.completeBookTalkCount = count;
    }

    public void setAdditionalInfo(MemberAdditionalInfoDto memberAdditionalInfoDto) {
        this.gender = memberAdditionalInfoDto.getGender();
        this.birth = memberAdditionalInfoDto.getBirth();
    }

    public void patchMyInfo(MyInfoDto myInfoDto) {
        this.gender = myInfoDto.getGender();
        this.birth = myInfoDto.getBirth();
        this.myCity = myInfoDto.getCity();
        this.marketingAgree = myInfoDto.getMarketingAgree();
    }

    public void addUserBooktalkandSortByStartDate(MemberBooktalk memberBooktalk) {
        this.getUserBookTalkList().add(memberBooktalk);
        // 시작날짜순으로 정렬(예정된 북토크)
        this.getUserBookTalkList().sort(Comparator.comparing(o -> o.getBooktalk().getStartDate()));
    }
}
