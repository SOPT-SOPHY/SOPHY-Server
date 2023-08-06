package org.sophy.sophy.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BooktalkRepository extends JpaRepository<Booktalk, Long> {

    Integer countAllByCityAndBooktalkStatus(City city, BooktalkStatus booktalkStatus);

    Integer countAllByBooktalkStatus(BooktalkStatus booktalkStatus);

    Integer countAllByCityAndCreateAtBetween(City city, LocalDateTime before, LocalDateTime now);

    default Booktalk getBooktalkById(Long booktalkId) {
        return this.findById(booktalkId)
            .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_BOOKTALK_EXCEPTION,
                ErrorStatus.NOT_FOUND_BOOKTALK_EXCEPTION.getMessage()));
    }

    @Query("select o.recruitScheduledBooktalks from Member m"
        + " join m.operatorProperty o"
        + " where m.email = :email")
    List<Booktalk> getWatingBooktalks(@Param("email") String email);
}
