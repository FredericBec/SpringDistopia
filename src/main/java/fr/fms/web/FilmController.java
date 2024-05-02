package fr.fms.web;

import fr.fms.business.IBusinessImpl;
import fr.fms.entities.Film;
import fr.fms.entities.Showing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class FilmController {

    @Autowired
    IBusinessImpl business;

    @GetMapping("/showings")
    public String showings(Long id, Model model){
        Optional<Film> optionalFilm = business.getFilmById(id);
        if(optionalFilm.isPresent()){
            Film film = optionalFilm.get();
            model.addAttribute("film", film);
        }
        List<Showing> showings = business.getShowingsByFilm(id);
        model.addAttribute("listShowing", showings);
        return "showings";
    }
}
