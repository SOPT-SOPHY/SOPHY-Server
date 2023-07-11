package org.sophy.sophy.infrastructure;

import org.sophy.sophy.domain.Booktalk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooktalkRepository extends JpaRepository<Booktalk, Long> {
import org.sophy.sophy.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooktalkRepository extends JpaRepository<Booktalk, Long> {

}
