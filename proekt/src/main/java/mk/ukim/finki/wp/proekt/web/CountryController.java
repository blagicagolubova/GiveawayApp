package mk.ukim.finki.wp.proekt.web;

import mk.ukim.finki.wp.proekt.model.enumerations.Continent;
import mk.ukim.finki.wp.proekt.model.enumerations.UserType;
import mk.ukim.finki.wp.proekt.sevice.CountryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/country")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/add-country")
    public String addCountryPage(Model model){
        model.addAttribute("continentList", Continent.values());
        return "add-country";
    }

    @PostMapping("/add")
    public String saveCountry(@RequestParam String name,
                              @RequestParam String code,
                              @RequestParam Continent continent,
                               HttpServletRequest request)
    {
        this.countryService.save(name, code, continent);
        return "redirect:/region/add-region";

    }
}
