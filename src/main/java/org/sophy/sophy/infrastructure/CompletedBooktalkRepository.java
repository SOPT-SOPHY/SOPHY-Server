package org.sophy.sophy.infrastructure;

import org.sophy.sophy.domain.CompletedBooktalk;
import org.sophy.sophy.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CompletedBooktalkRepository extends JpaRepository<CompletedBooktalk, Long> {
    List<CompletedBooktalk> findAllByMemberAndCreateAtBetween(Member member, LocalDateTime first, LocalDateTime last);
}
