package org.sophy.sophy.domain;

import lombok.AccessLevel;
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

    @Column(nullable = false)
    private boolean isAuthor;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private User(String nickname, boolean isAuthor, String email, String password) {
        this.nickname = nickname;
        this.isAuthor = isAuthor;
        this.email = email;
        this.password = password;
    }

    public static User newInstance(String nickname, boolean isAuthor, String email, String password) {
        return new User(nickname, isAuthor, email, password);
    }
}
