package mk.ukim.finki.wp.proekt.web;

import mk.ukim.finki.wp.proekt.model.Country;
import mk.ukim.finki.wp.proekt.model.Region;
import mk.ukim.finki.wp.proekt.sevice.CountryService;
import mk.ukim.finki.wp.proekt.sevice.RegionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import java.util.List;

@Controller
@RequestMapping("/region")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RegionController {
    private final RegionService regionService;
    private final CountryService countryService;



    public RegionController(RegionService regionService, CountryService countryService) {
        this.regionService = regionService;
        this.countryService = countryService;
    }

    @GetMapping
    public String getRegionPage(Model model,HttpServletRequest request){
        String username=request.getRemoteUser();
        List<Region> regions = this.regionService.findAll();
        model.addAttribute("regions",regions);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","list-region");
        return "master-template";
    }


    @GetMapping("/add-region")
    public String addRegionPage(Model model,HttpServletRequest request){
        String username=request.getRemoteUser();
        List<Country> countries= this.countryService.findAll();
        model.addAttribute("countries",countries);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","add-region");
        return "master-template";
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
    public String editRegion(@PathVariable Integer id, Model model, HttpServletRequest request){
        String username=request.getRemoteUser();
        Region region=this.regionService.findById(id);
        List<Country> regionCountries=region.getCountries();
        List<Country> countries=this.countryService.findAll();
        model.addAttribute("region", region);
        model.addAttribute("regionCountries",regionCountries);
        model.addAttribute("countries",countries);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","add-region");
        return "master-template";
    }
}
