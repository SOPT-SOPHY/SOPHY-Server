package org.sophy.sophy.domain;

import lombok.*;
import org.sophy.sophy.domain.enumerate.BookCategory;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String title;
    private BookCategory bookCategory;
    private Integer booktalkOpenCount;
    private Boolean isRegistration;
    @Column(length = 1000)
    private String bookImageUrl;
    @ManyToOne
    @JoinColumn(name = "author_property_id")
    private AuthorProperty authorProperty;

    public void setAuthorProperty(AuthorProperty authorProperty) {
        this.authorProperty = authorProperty;
        authorProperty.getMyBookList().add(this);
    }
}
