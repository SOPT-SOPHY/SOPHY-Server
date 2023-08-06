package org.sophy.sophy.infrastructure.query;

import java.util.List;
import org.sophy.sophy.domain.Book;
import org.sophy.sophy.domain.dto.mypage.MyBookDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookQueryRepository extends JpaRepository<Book, Long> {

    @Query("select new org.sophy.sophy.domain.dto.mypage.MyBookDto("
        + " b.title, b.bookCategory, b.booktalkOpenCount, b.isRegistration, b.bookImageUrl)"
        + " from Book b"
        + " where b.id in :bookIds")
    List<MyBookDto> findBooks(
        @Param("bookIds") List<Long> bookIds);
}
