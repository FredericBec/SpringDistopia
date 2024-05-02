package fr.fms.dao;

import fr.fms.entities.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    Page<Cinema> findByNameContains(String name, Pageable pageable);
    Page<Cinema> findByCityId(Long cityId, Pageable pageable);
    List<Cinema> findByCityId(Long cityId);
    List<Cinema> findByNameContains(String name);
}
