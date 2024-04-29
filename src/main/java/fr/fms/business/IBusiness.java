package fr.fms.business;

import fr.fms.entities.Cinema;
import fr.fms.entities.City;
import fr.fms.entities.Film;
import fr.fms.entities.Showing;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBusiness {

    Page<Cinema> getCinemasPages(String kw, int page);
    Page<Cinema> getCinemaByCityPage(Long cityId, int page);
    List<City> getCities();
    Page<Film> getFilmsByCinemaPage(Long cinemaId, int page);
    void getShowingsByFilm();
    void saveCity();
    void saveCinema();
    void saveShowing();
    City getCityById(Long id);
    void deleteCityById(Long id);
    Cinema getCinemaById(Long id);
    void deleteCinemaById(Long id);
    Showing getShowingById(Long id);
    void deleteShowingById(Long id);

}
