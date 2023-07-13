package org.sophy.sophy.infrastructure;

import org.sophy.sophy.domain.MemberBooktalk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberBooktalkRepository extends JpaRepository<MemberBooktalk, Long> {
}
