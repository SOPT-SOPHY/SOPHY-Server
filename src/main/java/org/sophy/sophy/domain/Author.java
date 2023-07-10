package org.sophy.sophy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Booktalk> myBookTalkList;
    //private List<Book> myBookList
    private Integer matchingBookTalkCount;
    private Integer recruitBookTalkCount;
}
