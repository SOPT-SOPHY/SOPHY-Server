package org.sophy.sophy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    private Long id;

//    private List<Booktalk> myBookTalkList;
    //private List<Book> myBookList
    private Integer matchingBookTalkCount;
    private Integer recruitBookTalkCount;
}
