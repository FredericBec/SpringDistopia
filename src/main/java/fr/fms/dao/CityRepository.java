package fr.fms.dao;

import fr.fms.entities.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    Page<City> findByNameContains(String name, Pageable pageable);
}
