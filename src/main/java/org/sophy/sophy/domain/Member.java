package org.sophy.sophy.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.controller.dto.request.MemberRequestDto;
import org.sophy.sophy.domain.dto.mypage.MyInfoDto;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.domain.enumerate.City;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import org.sophy.sophy.domain.common.AuditingTimeEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE member_id=?")
@Where(clause = "deleted=false")
public class Member extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String socialId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String password;

    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phoneNum;

    private String gender;
    private String birth;

    private City myCity;

    private Boolean marketingAgree;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "member")
    private List<MemberBooktalk> userBookTalkList; // -> 참여 예정 북토크 수

    private Integer userBookTalkSize;

    @OneToMany(mappedBy = "member")
    private List<CompletedBooktalk> completedBookTalkList; //이거 길이로 북토크 수 보여줄 수 있지 않을까? -> 참여 완료 북토크 수

    private Integer completedBookTalkSize;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_property_id")
    private AuthorProperty authorProperty; //(개설한 북토크 리스트 + 나의 책 리스트 + 공간 매칭 중 북토크 수 + 청중 모집 중 북토크 수)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_property_id")
    private OperatorProperty operatorProperty;

    private final Boolean deleted = Boolean.FALSE;

    @Builder
    public Member(String name, String email, String password, String phoneNum,
        boolean marketingAgree, Authority authority, String socialId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.marketingAgree = marketingAgree;
        this.authority = authority;
        this.socialId = socialId;
        this.userBookTalkList = new ArrayList<>();
        this.userBookTalkSize = 0;
        this.completedBookTalkList = new ArrayList<>();
        this.completedBookTalkSize = 0;
    }

    public void plusUserBooktalk() {
        this.userBookTalkSize += 1;
    }

    public void minusUserBooktalk() {
        this.userBookTalkSize -= 1;
    }

    public void plusCompletedBooktalk() {
        this.completedBookTalkSize += 1;
    }

    public void setAuthorProperty(AuthorProperty authorProperty) {
        this.authorProperty = authorProperty;
    }

    public void setOperatorProperty(OperatorProperty operatorProperty) {
        this.operatorProperty = operatorProperty;
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

    public Member createSocialLogin(PasswordEncoder passwordEncoder, MemberRequestDto memberRequestDto) {
        this.email = memberRequestDto.getEmail();
        this.name = memberRequestDto.getName();
        this.password = passwordEncoder.encode(memberRequestDto.getPassword());
        this.phoneNum = memberRequestDto.getPhoneNum();
        this.marketingAgree = memberRequestDto.getMarketingAgree();
        this.authority = Authority.USER;
        return this;
    }
}
