package mk.ukim.finki.wp.proekt.web;

import mk.ukim.finki.wp.proekt.model.Country;
import mk.ukim.finki.wp.proekt.sevice.CountryService;
import mk.ukim.finki.wp.proekt.sevice.RegionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/region")
public class RegionController {
    private final RegionService regionService;
    private final CountryService countryService;



    public RegionController(RegionService regionService, CountryService countryService) {
        this.regionService = regionService;
        this.countryService = countryService;
    }

    @GetMapping("/add-region")
    public String addRegionPage(Model model){
        List<Country> countries= this.countryService.findAll();
        model.addAttribute("countries",countries);
        return "add-region";
    }

    @PostMapping("/add")
    public String saveRegion(@RequestParam String name,
                             @RequestParam List<Integer> countries,
                             HttpServletRequest request)
    {
        this.regionService.save(name,countries);
        return "redirect:/region/list-region";
    }
}
