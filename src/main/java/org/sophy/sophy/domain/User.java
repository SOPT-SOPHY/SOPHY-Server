package org.sophy.sophy.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Builder.Default // 기본값 설정
    @Column(nullable = false)
    private boolean isAuthor = false;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder
    public User(String nickname, boolean isAuthor, String email, String password) {
        this.nickname = nickname;
        this.isAuthor = isAuthor;
        this.email = email;
        this.password = password;
    }
}
