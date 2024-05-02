package fr.fms.web;

import fr.fms.business.IBusinessImpl;
import fr.fms.entities.Cinema;
import fr.fms.entities.City;
import fr.fms.entities.Film;
import fr.fms.exceptions.ManageErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CinemaController {

    @Autowired
    IBusinessImpl business;

    private final Logger logger = LoggerFactory.getLogger(CinemaController.class);

    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "keyword", defaultValue = "") String kw,
                                    @RequestParam(name="cityId" , defaultValue = "0") Long cityId,
                                    @ModelAttribute(name="error") String error){
        Page<Cinema> cinemas = null;
        model.addAttribute("error", model.getAttribute("error"));
        try{
            if(cityId > 0){
                cinemas = business.getCinemaByCityPage(cityId, page);
                if(cinemas.isEmpty()){
                    model.addAttribute("error", ManageErrors.STR_ERROR);
                }
            }else cinemas = business.getCinemasPages(kw,page);
            model.addAttribute("cityId", cityId);
            model.addAttribute("listCinema", cinemas.getContent());
            model.addAttribute("pages", new int[cinemas.getTotalPages()]);
            model.addAttribute("currentPage", page);
            model.addAttribute("keyword",kw);
            model.addAttribute("cities", business.getCities());

        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            logger.error("[CINEMA CONTROLLER : INDEX] : {} " , e.getMessage());
        }
        return "cinemas";
    }

    @GetMapping("/cinema_detail")
    public String cinema(Long id, Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(name="cityId" , defaultValue = "0") Long cityId){
        Page<Film> films = business.getFilmsByCinemaPage(id, page);
        model.addAttribute("cityId", cityId);
        model.addAttribute("listFilm", films.getContent());
        model.addAttribute("pages", new int[films.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "films";
    }

    @GetMapping("/cinema")
    public String addCinema(Model model){
        model.addAttribute("cinema", new Cinema());
        try {
            model.addAttribute("cities", business.getCities());
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            logger.error("[CINEMA CONTROLLER : MANAGE NEW CINEMA] : {} " , e.getMessage());
        }
        return "cinema";
    }

    @PostMapping("/saveCinema")
    public String saveCinema(@Valid Cinema cinema, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs){
        try {
            if(bindingResult.hasErrors()) {
                //model.addAttribute("cities", business.getCities());
                return "city";
            }
            business.saveCinema(cinema);
        }catch (Exception e){
            redirectAttrs.addAttribute("error",e.getMessage());
            logger.error("[CINEMA CONTROLLER : SAVE CINEMA] : {} " , e.getMessage());
        }
        return "redirect:/index";
    }

    @GetMapping("/deleteCinema")
    public String deleteCinema(Long id, int page, String keyword, RedirectAttributes redirectAttrs){
        try{
            business.deleteCinemaById(id);
        }catch (Exception e){
            redirectAttrs.addAttribute("error",e.getMessage());
            logger.error("[CINEMA CONTROLLER : DELETE] : {} " , e.getMessage());
        }
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/editCinema")
    public String editCinema(Long id, Model model){
        Cinema cinema;
        try {
            cinema = business.getCinemaById(id);
            model.addAttribute("cities", business.getCities());
            model.addAttribute("cinema", cinema);
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            logger.error("[CINEMA CONTROLLER : EDIT] : {} " , e.getMessage());
        }
        return "editCinema";
    }
}
