package fr.fms.dao;

import fr.fms.entities.Showing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowingRepository extends JpaRepository<Showing, Long> {

    List<Showing> findByFilmsId(Long filmId);
}
