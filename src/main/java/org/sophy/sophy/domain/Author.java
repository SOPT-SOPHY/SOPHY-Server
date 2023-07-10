package org.sophy.sophy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue
    @Column(name = "author_id")
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
