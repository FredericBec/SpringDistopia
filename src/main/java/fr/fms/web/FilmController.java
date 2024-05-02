package fr.fms.web;

import fr.fms.business.IBusinessImpl;
import fr.fms.entities.Cinema;
import fr.fms.entities.Film;
import fr.fms.entities.Showing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class FilmController {

    @Autowired
    IBusinessImpl business;

    private final Logger logger = LoggerFactory.getLogger(CinemaController.class);

    @GetMapping("/showings")
    public String showings(Long id, Model model){
        Film film = business.getFilmById(id);

        model.addAttribute("film", film);

        List<Showing> showings = business.getShowingsByFilm(id);
        model.addAttribute("listShowing", showings);
        return "showings";
    }

    @GetMapping("/film")
    public String addFilm(@RequestParam(name="cityId" , defaultValue = "0") Long cityId, Model model){
        model.addAttribute("film", new Film());
        try {
            model.addAttribute("cinemas", business.getCinemasByCity(cityId));
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            logger.error("[FILM CONTROLLER : MANAGE NEW FILM] : {} " , e.getMessage());
        }
        return "film";
    }

    @PostMapping("/saveFilm")
    public String saveFilm(@Valid Film film, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs){
        try {
            if(bindingResult.hasErrors()) {
                model.addAttribute("cinemas", business.getCinemas());
                return "film";
            }
            business.saveFilm(film);
        }catch (Exception e){
            redirectAttrs.addAttribute("error",e.getMessage());
            logger.error("[FILM CONTROLLER : SAVE FILM] : {} " , e.getMessage());
        }
        return "redirect:/cinema_detail";
    }

    @GetMapping("/deleteFilm")
    public String deleteFilm(Long id, int page, String keyword, RedirectAttributes redirectAttrs){
        try{
            business.deleteFilmById(id);
        }catch (Exception e){
            redirectAttrs.addAttribute("error",e.getMessage());
            logger.error("[FILM CONTROLLER : DELETE] : {} " , e.getMessage());
        }
        return "redirect:/cinema_detail?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/editFilm")
    public String editFilm(@RequestParam(name="cityId" , defaultValue = "0") Long cityId, Long id, Model model){
       Film film;
        try {
            film = business.getFilmById(id);
            model.addAttribute("cinemas", business.getCinemasByCity(cityId));
            model.addAttribute("film", film);
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            logger.error("[FILM CONTROLLER : EDIT] : {} " , e.getMessage());
        }
        return "film";
    }
}
