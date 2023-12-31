package org.sophy.sophy.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.controller.dto.request.MemberRequestDto;
import org.sophy.sophy.domain.common.AuditingTimeEntity;
import org.sophy.sophy.domain.dto.mypage.MyInfoDto;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.domain.enumerate.City;
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

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "member")
    private List<MemberBooktalk> userBookTalkList; // -> 참여 예정 북토크 수

    @OneToMany(mappedBy = "member")
    private List<CompletedBooktalk> completedBookTalkList; //이거 길이로 북토크 수 보여줄 수 있지 않을까? -> 참여 완료 북토크 수

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
        this.completedBookTalkList = new ArrayList<>();
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

    public Member socialSignUp(PasswordEncoder passwordEncoder, MemberRequestDto memberRequestDto) {
        this.email = memberRequestDto.getEmail();
        this.name = memberRequestDto.getName();
        this.password = passwordEncoder.encode(memberRequestDto.getPassword());
        this.phoneNum = memberRequestDto.getPhoneNum();
        this.marketingAgree = memberRequestDto.getMarketingAgree();
        this.authority = Authority.USER;
        return this;
    }

    public void patchProfileImage(String profileImageUrl) {
        this.profileImage = profileImageUrl;
    }
}
