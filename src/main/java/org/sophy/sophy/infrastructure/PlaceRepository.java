package org.sophy.sophy.infrastructure;

import java.util.List;
import org.sophy.sophy.domain.Place;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByCityIn(List<City> cities);

    List<Place> findAll();

    default Place getPlaceById(Long placeId) {
        return this.findById(placeId)
            .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_PLACE_EXCEPTION,
                ErrorStatus.NOT_FOUND_PLACE_EXCEPTION.getMessage()));
    }
}
