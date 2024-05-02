package fr.fms.web;

import fr.fms.business.IBusinessImpl;
import fr.fms.dao.CityRepository;
import fr.fms.entities.City;
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
import java.util.Optional;

@Controller
public class CityController {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    IBusinessImpl business;

    private final Logger logger = LoggerFactory.getLogger(CityController.class);

    @GetMapping("/city")
    public String cities(Long id, Model model){
        Long cityId = (long)-1;
        try{
            Optional<City> cityOptional = cityRepository.findById(id+1);
            if (cityOptional.isPresent()){
                City city = cityOptional.get();
                cityId = city.getId();
                model.addAttribute("cityId", cityId);
            }else return "redirect:/index?error=" + ManageErrors.STR_ERROR;
        }catch (Exception e){
            logger.error("[CITY CONTROLLER : CITY] : {} " , e.getMessage());
            return "redirect:/index?error=" + e.getMessage();
        }
        return "redirect:/index?cityId=" + cityId;
    }

    @GetMapping("/cities")
    public String getCities(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "keyword", defaultValue = "") String kw,
                            @ModelAttribute(name="error") String error){
        Page<City> cities = null;
        model.addAttribute("error", model.getAttribute("error"));
        try {
            cities = business.getCitiesPages(kw, page);
            model.addAttribute("listCity", cities.getContent());
            model.addAttribute("pages", new int[cities.getTotalPages()]);
            model.addAttribute("currentPage", page);
            model.addAttribute("keyword",kw);
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            logger.error("[CITY CONTROLLER : INDEX] : {} " , e.getMessage());
        }
        return "cities";
    }

    @GetMapping("/addCity")
    public String addCity(Model model){
        model.addAttribute("city", new City());
        return "city";
    }

    @PostMapping("/saveCity")
    public String saveCity(@Valid City city, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs){
        try {
            if(bindingResult.hasErrors()) return "city";
            business.saveCity(city);
        }catch (Exception e){
            redirectAttrs.addAttribute("error",e.getMessage());
            logger.error("[CITY CONTROLLER : SAVE CITY] : {} " , e.getMessage());
        }
        return "redirect:/cities";
    }

    @GetMapping("/deleteCity")
    public String delete(Long id, int page, String keyword, RedirectAttributes redirectAttrs){
        try{
            business.deleteCityById(id);
        }catch (Exception e){
            redirectAttrs.addAttribute("error",e.getMessage());
            logger.error("[CITY CONTROLLER : DELETE] : {} " , e.getMessage());
        }
        return "redirect:/cities?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/editCity")
    public String editCity(Long id, Model model){
        City city;
        try {
            city = business.getCityById(id);
            model.addAttribute("city", city);
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            logger.error("[CITY CONTROLLER : EDIT] : {} " , e.getMessage());
        }
        return "city";
    }
}
