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
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public Page<Film> getFilmsByCinemaPage(Long cinemaId, int page) {
        return filmRepository.findByCinemasId(cinemaId, PageRequest.of(page, 5));
    }

    @Override
    public void getShowingsByFilm() {

    }

    @Override
    public void saveCity() {

    }

    @Override
    public void saveCinema() {

    }

    @Override
    public void saveShowing() {

    }

    @Override
    public City getCityById(Long id) {
        return null;
    }

    @Override
    public void deleteCityById(Long id) {

    }

    @Override
    public Cinema getCinemaById(Long id) {
        return null;
    }

    @Override
    public void deleteCinemaById(Long id) {

    }

    @Override
    public Showing getShowingById(Long id) {
        return null;
    }

    @Override
    public void deleteShowingById(Long id) {

    }
}
