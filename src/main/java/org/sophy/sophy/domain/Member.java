package org.sophy.sophy.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sophy.sophy.controller.dto.request.MemberAdditionalInfoDto;
import org.sophy.sophy.domain.dto.MyInfoDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member extends AuditingTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNum;

    private String gender;
    private String birth;

    private City myCity;

    private boolean marketingAgree;

    @Column(nullable = false)
    private boolean isAuthor;

    @Column(nullable = false)
    private boolean isOperator;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private Integer bookCount;
    private Integer bookTalkCount;

    //private List<Booktalk> userBookTalk list
    @OneToOne
    private Author author; //(개설한 북토크 리스트 + 나의 책 리스트 + 공간 매칭 중 북토크 수 + 청중 모집 중 북토크 수)

    @Builder
    public Member(String name, String email, String password, String phoneNum, String gender, String birth
            , City myCity, boolean marketingAgree, boolean isAuthor, boolean isOperator, Authority authority) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.birth = birth;
        this.myCity = myCity;
        this.marketingAgree = marketingAgree;
        this.isAuthor = isAuthor;
        this.isOperator = isOperator;
        this.authority = authority;
    }

    public void serAuthor(Author author) {
        this.author = author;
    }

    public void setBookCount(int count) {
        this.bookCount = count;
    }

    public void setBookTalkCount(int count) {
        this.bookTalkCount = count;
    }

    public void setAdditionalInfo(MemberAdditionalInfoDto memberAdditionalInfoDto) {
        this.gender = memberAdditionalInfoDto.getGender();
        this.birth = memberAdditionalInfoDto.getBirth();
    }

    public void patchMyInfo(MyInfoDto myInfoDto) {
        this.gender = myInfoDto.getGender();
        this.birth = myInfoDto.getBirth();
        this.myCity = myInfoDto.getCity();
        this.marketingAgree = myInfoDto.isMarketingAgree();
    }
}
