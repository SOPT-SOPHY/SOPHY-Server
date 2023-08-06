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

    private Integer myBookTalkSize;

    public void plusMyBookTalkSize() {
        this.myBookTalkSize += 1;
    }

    public void minusMyBookTalkSize() { // 북토크가 완료 되었을 때 (개최한 북토크 -> 참여한 북토크로 변하는지에 따라 나뉠듯)
        this.myBookTalkSize -= 1;
    }

    @OneToMany(mappedBy = "authorProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Book> myBookList;

    public void deleteBooktalk(Booktalk booktalk) {
        myBookTalkList.remove(booktalk);
    }

    public static AuthorProperty toBuild() {
        return AuthorProperty.builder()
            .myBookTalkSize(0)
            .myBookList(new ArrayList<>())
            .myBookTalkList(new ArrayList<>())
            .build();
    }
}
