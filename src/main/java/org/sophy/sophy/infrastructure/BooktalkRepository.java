package org.sophy.sophy.infrastructure;

import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface BooktalkRepository extends JpaRepository<Booktalk, Long> {
    List<Booktalk> findAllByCity(City city);

    Integer countAllByCityAndCreateAtBetween(City city, LocalDateTime before, LocalDateTime now);

    //sql의 in절을 통해 모집중과 모집 마감 북토크들을 한번에 조회
    @Query("select b from Booktalk b where b.booktalkStatus in :booktalkStatuses")
    List<Booktalk> findByBooktalkStatuses(@Param("booktalkStatuses") List<BooktalkStatus> booktalkStatuses);

    default Booktalk getBooktalkById(Long booktalkId) {
        return this.findById(booktalkId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_BOOKTALK_EXCEPTION, ErrorStatus.NOT_FOUND_BOOKTALK_EXCEPTION.getMessage()));
    }
}
