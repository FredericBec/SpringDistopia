package fr.fms.business;

import fr.fms.dao.CinemaRepository;
import fr.fms.dao.CityRepository;
import fr.fms.dao.FilmRepository;
import fr.fms.dao.ShowingRepository;
import fr.fms.entities.Cinema;
import fr.fms.entities.City;
import fr.fms.entities.Film;
import fr.fms.entities.Showing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IBusinessImpl implements IBusiness{

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CinemaRepository cinemaRepository;

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    ShowingRepository showingRepository;

    @Override
    public Page<Cinema> getCinemasPages(String kw, int page) {
        return cinemaRepository.findByNameContains(kw, PageRequest.of(page, 5));
    }

    @Override
    public Page<Cinema> getCinemaByCityPage(Long cityId, int page) {
        return cinemaRepository.findByCityId(cityId, PageRequest.of(page, 5));
    }

    @Override
    public List<Cinema> getCinemasByCity(Long id) {
        return cinemaRepository.findByCityId(id);
    }

    @Override
    public List<Cinema> getCinemas() {
        return cinemaRepository.findAll();
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public Page<City> getCitiesPages(String kw, int page) {
        return cityRepository.findByNameContains(kw, PageRequest.of(page, 5));
    }

    @Override
    public Page<Film> getFilmsByCinemaPage(Long cinemaId, int page) {
        return filmRepository.findByCinemasId(cinemaId, PageRequest.of(page, 5));
    }

    @Override
    public List<Showing> getShowingsByFilm(Long filmId) {
        return showingRepository.findByFilmsId(filmId);
    }

    @Override
    public void saveCity(City city) {
        cityRepository.save(city);
    }

    @Override
    public void saveCinema(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    @Override
    public void saveFilm(Film film) {
        filmRepository.save(film);
    }

    @Override
    public void saveShowing(Showing showing) {
        showingRepository.save(showing);
    }

    @Override
    public City getCityById(Long id) {
        Optional<City> optional = cityRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public void deleteCityById(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public Cinema getCinemaById(Long id) throws Exception{
        Optional<Cinema> optional = cinemaRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public void deleteCinemaById(Long id) {
        cinemaRepository.deleteById(id);
    }

    @Override
    public Film getFilmById(Long id) {
        Optional<Film> optional = filmRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public void deleteFilmById(Long id) {
        filmRepository.deleteById(id);
    }

    @Override
    public Showing getShowingById(Long id) {
        Optional<Showing> optional = showingRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public void deleteShowingById(Long id) {
        showingRepository.deleteById(id);
    }
}
