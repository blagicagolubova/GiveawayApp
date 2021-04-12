package mk.ukim.finki.wp.proekt.web;

import mk.ukim.finki.wp.proekt.model.Country;
import mk.ukim.finki.wp.proekt.model.Region;
import mk.ukim.finki.wp.proekt.sevice.CountryService;
import mk.ukim.finki.wp.proekt.sevice.RegionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
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

    @GetMapping
    public String getRegionPage(Model model){
        List<Region> regions = this.regionService.findAll();
        model.addAttribute("regions",regions);
        return "list-region";
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
        return "redirect:/region";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String editRegion(@PathVariable Integer id, Model model){
        Region region=this.regionService.findById(id);
        List<Country> regionCountries=region.getCountries();
        List<Country> countries=this.countryService.findAll();
        model.addAttribute("region", region);
        model.addAttribute("regionCountries",regionCountries);
        model.addAttribute("countries",countries);
        return "add-region";
    }
}
