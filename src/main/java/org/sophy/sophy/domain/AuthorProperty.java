package org.sophy.sophy.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AuthorProperty {
    @Id
    @GeneratedValue
    @Column(name = "author_property_id")
    private Long id;

    @OneToMany
    private List<Booktalk> myBookTalkList;
    //private List<Book> myBookList
    private Integer matchingBookTalkCount;
    private Integer recruitBookTalkCount;

    public void deleteBooktalk(Booktalk booktalk) {
        myBookTalkList.remove(booktalk);
    }
}
