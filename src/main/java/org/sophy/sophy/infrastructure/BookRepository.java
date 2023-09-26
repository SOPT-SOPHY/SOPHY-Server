package org.sophy.sophy.infrastructure;

import org.sophy.sophy.domain.AuthorProperty;
import org.sophy.sophy.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Integer countBookByAuthorProperty(AuthorProperty authorProperty);
}
