package org.sophy.sophy.domain;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "authorProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Booktalk> myBookTalkList;

    @OneToMany(mappedBy = "authorProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Book> myBookList;

    public void deleteBooktalk(Booktalk booktalk) {
        myBookTalkList.remove(booktalk);
    }

    public static AuthorProperty toBuild() {
        return AuthorProperty.builder()
            .myBookList(new ArrayList<>())
            .myBookTalkList(new ArrayList<>())
            .build();
    }
}
