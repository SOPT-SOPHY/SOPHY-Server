package org.sophy.sophy.infrastructure;

import org.sophy.sophy.domain.Booktalk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooktalkRepository extends JpaRepository<Booktalk, Long> {
}
