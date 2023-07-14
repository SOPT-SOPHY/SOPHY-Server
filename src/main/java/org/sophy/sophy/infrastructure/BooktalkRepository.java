package org.sophy.sophy.infrastructure;

import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.enumerate.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface BooktalkRepository extends JpaRepository<Booktalk, Long> {
    public List<Booktalk> findAllByCity(City city);

    public Integer countAllByCityAndCreateAtBetween(City city, LocalDateTime before, LocalDateTime now);
}
