package mk.ukim.finki.wp.proekt.web;

import mk.ukim.finki.wp.proekt.model.*;
import mk.ukim.finki.wp.proekt.model.enumerations.UserType;
import mk.ukim.finki.wp.proekt.sevice.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/giveaway")
public class GiveawayController {
    private final GiveawayService giveawayService;
    private final RegionService regionService;
    private final CountryService countryService;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;



    public GiveawayController(GiveawayService giveawayService, RegionService regionService, CountryService countryService, ManufacturerService manufacturerService, CategoryService categoryService) {
        this.giveawayService = giveawayService;
        this.regionService = regionService;
        this.countryService = countryService;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getGiveawayPage(Model model){
        List<Giveaway> giveawayList=this.giveawayService.findAll();
        model.addAttribute("giveawayList",giveawayList);
        return "giveaway";
    }

    @GetMapping("/add-giveaway")
    public String addGiveawayPage(Model model){
        List<Region> regions=this.regionService.findAll();
        List<Country> countries=this.countryService.findAll();
        List<Manufacturer> manufacturers=this.manufacturerService.findAll();
        List<Category> categories=this.categoryService.findAll();
        model.addAttribute("user_types", UserType.values());
        model.addAttribute("regions",regions);
        model.addAttribute("categories",categories);
        model.addAttribute("countries",countries);
        model.addAttribute("manufacturers",manufacturers);
        return "add-giveaway";
    }

    @PostMapping("/add")
    public String saveGiveaway(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam Date startdate,
            @RequestParam Date enddate,
            @RequestParam String award,
            @RequestParam Integer region_Id,
            @RequestParam String country,
            @RequestParam Integer category_Id,
            @RequestParam String manufacturer,
            @RequestParam UserType user_type,
            HttpServletRequest request){
        Manufacturer manufacturer1=new Manufacturer(manufacturer);
        String username=request.getRemoteUser();
        Award award1=new Award(award,manufacturer1);
        this.giveawayService.save(name,startdate,enddate,category_Id,award1.getId(), username,region_Id);

        return "redirect:/giveaway";
    }





}
