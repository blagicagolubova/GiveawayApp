package mk.ukim.finki.wp.proekt.web;

import mk.ukim.finki.wp.proekt.model.*;
import mk.ukim.finki.wp.proekt.model.enumerations.AwardStatus;
import mk.ukim.finki.wp.proekt.model.enumerations.UserType;
import mk.ukim.finki.wp.proekt.sevice.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private final UserService userService;
    private final AwardService awardService;
    private final GiveawayRegionService giveawayRegionService;
    private final CompanyService companyService;



    public GiveawayController(GiveawayService giveawayService, RegionService regionService, CountryService countryService, ManufacturerService manufacturerService, CategoryService categoryService, UserService userService, AwardService awardService, GiveawayRegionService giveawayRegionService, CompanyService companyService) {
        this.giveawayService = giveawayService;
        this.regionService = regionService;
        this.countryService = countryService;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.awardService = awardService;
        this.giveawayRegionService = giveawayRegionService;
        this.companyService = companyService;
    }

    @GetMapping
    public String getGiveawayPage(Model model){
        List<Giveaway> giveawayList=this.giveawayService.findAll();
        model.addAttribute("giveawayList",giveawayList);
        return "giveaway";
    }

    @GetMapping("/add-giveaway")
    public String addGiveawayPage(Model model,
                                  @ModelAttribute("region") Region region,
                                  @ModelAttribute("country") Country country){
        GiveawayRegion giveawayRegion=new GiveawayRegion();
        List<Region> regions=this.regionService.findAll();
        List<Award> awards= this.awardService.findAllByStatus(AwardStatus.DEACTIVE);
        List<Manufacturer> manufacturers=this.manufacturerService.findAll();
        List<Category> categories=this.categoryService.findAll();
        model.addAttribute("user_types", UserType.values());
        model.addAttribute("regions",regions);
        model.addAttribute("categories",categories);
        model.addAttribute("giveawayRegion", giveawayRegion);
        model.addAttribute("awards", awards);
        model.addAttribute("manufacturers",manufacturers);
        return "add-giveaway";
    }

    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    public @ResponseBody
    List<Country> findAllCountries(
            @RequestParam(value = "region_id", required = true) Integer region_id) {
        Region region = regionService.findById(region_id);
        return region.getCountries();
    }

    @PostMapping("/add")
    public String saveGiveaway(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String startdate,
            @RequestParam String enddate,
            @RequestParam Integer award,
            @RequestParam Integer region,
            @RequestParam List<Integer> country,
            @RequestParam Integer category,
            @RequestParam UserType user_type,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String desc,
            HttpServletRequest request) throws ParseException {
        Date start=new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
        Date end=new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
        String username=request.getRemoteUser();
        //User user= this.userService.findByUsername(username);
        GiveawayRegion giveawayRegion=this.giveawayRegionService.save(country);
        this.awardService.updateStatus(award,AwardStatus.ACTIVE);
        if (user_type.equals(UserType.PRIVATE)) {
            this.giveawayService.save(name, start, end, category, award, user_type, username, giveawayRegion.getId(), null);
        }
        else{
            if(this.companyService.findBYName(companyName).isPresent()){
                Integer cid = this.companyService.findBYName(companyName).get().getId();
                this.giveawayService.save(name, start, end, category, award, user_type,username,giveawayRegion.getId(),cid);
            }
            else {
                Company company=this.companyService.save(companyName,address,desc);
                this.giveawayService.save(name, start, end, category, award, user_type,username,giveawayRegion.getId(),company.getId());

            }
        }

        return "redirect:/giveaway";
    }





}
