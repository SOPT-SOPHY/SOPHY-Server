package org.sophy.sophy.infrastructure;

import org.sophy.sophy.domain.Booktalk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooktalkRepository extends JpaRepository<Booktalk, Long> {
    List<Booktalk> findAllByAuthorId(Long authorId); //Booktalk의 작가
}
