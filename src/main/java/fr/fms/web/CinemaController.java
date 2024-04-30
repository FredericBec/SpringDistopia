package fr.fms.web;

import fr.fms.business.IBusinessImpl;
import fr.fms.entities.Cinema;
import fr.fms.exceptions.ManageErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

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
}
