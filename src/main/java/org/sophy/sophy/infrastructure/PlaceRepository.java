package org.sophy.sophy.infrastructure;

import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findAllByCity(City city);

    List<Place> findAll();
}
