package fr.fms.dao;

import fr.fms.entities.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    Page<Cinema> findByName(String name, Pageable pageable);
    Page<Cinema> findByCityId(Long cityId, Pageable pageable);
}
