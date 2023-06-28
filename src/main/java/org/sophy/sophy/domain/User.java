package org.sophy.sophy.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private boolean isAuthor;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
}
