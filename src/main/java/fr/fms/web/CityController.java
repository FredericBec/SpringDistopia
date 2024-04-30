package fr.fms.web;

import fr.fms.dao.CityRepository;
import fr.fms.entities.City;
import fr.fms.exceptions.ManageErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class CityController {

    @Autowired
    CityRepository cityRepository;

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
}
