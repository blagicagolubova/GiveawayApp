package mk.ukim.finki.wp.proekt.web;

import mk.ukim.finki.wp.proekt.model.*;
import mk.ukim.finki.wp.proekt.model.enumerations.AwardStatus;
import mk.ukim.finki.wp.proekt.model.enumerations.GiveawayStatus;
import mk.ukim.finki.wp.proekt.model.enumerations.Role;
import mk.ukim.finki.wp.proekt.model.enumerations.UserType;
import mk.ukim.finki.wp.proekt.sevice.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.DocumentException;

import java.io.IOException;
import java.text.DateFormat;
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
    public String getGiveawayPage(@RequestParam(required = false) String categorySearch, @RequestParam(required = false) String statusSearch, Model model, HttpServletRequest request){
        List<Giveaway> giveaways;
        String username=request.getRemoteUser();
        User user= this.userService.findByUsername(username);
        if (user.getRole()== Role.ROLE_USER){
            if(categorySearch==null){
                giveaways=this.giveawayService.findAvailableForParticipation(username);
            }
            else {
                giveaways=this.giveawayService.listByCategory(categorySearch, username);
            }
        }
        else {
            giveaways=this.giveawayService.findAllByStatusAndCategory(statusSearch, categorySearch);

        }
        model.addAttribute("categoryList", this.categoryService.findAll());
        model.addAttribute("status", GiveawayStatus.values());
        model.addAttribute("giveawayList", giveaways);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","giveaway");
        return "master-template";
    }

    @GetMapping("/add-giveaway")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String addGiveawayPage(Model model,
                                  @ModelAttribute("region") Region region,
                                  @ModelAttribute("country") Country country,
                                  HttpServletRequest request){
        String username=request.getRemoteUser();
        GiveawayRegion giveawayRegion=new GiveawayRegion();
        List<Region> regions=this.regionService.findAll();
        List<Award> awards= this.awardService.findAllByStatusAndUser(AwardStatus.DEACTIVE, username);
        List<Manufacturer> manufacturers=this.manufacturerService.findAll();
        List<Category> categories=this.categoryService.findAll();
        model.addAttribute("user_types", UserType.values());
        model.addAttribute("regions",regions);
        model.addAttribute("categories",categories);
        model.addAttribute("giveawayRegion", giveawayRegion);
        model.addAttribute("awards", awards);
        model.addAttribute("manufacturers",manufacturers);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","add-giveaway");
        return "master-template";
    }

    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    public @ResponseBody
    List<Country> findAllCountries(
            @RequestParam(value = "region_id", required = true) Integer region_id) {
        Region region = regionService.findById(region_id);
        return region.getCountries();
    }

    @RequestMapping(value = "/details/{id}", method =RequestMethod.GET)
    public String GiveawayDetails(@PathVariable Integer id, Model model,@RequestParam(required = false) String message, HttpServletRequest request){
        if (message != null && !message.isEmpty()) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("message", message);
        }
        if(this.giveawayService.checkIfGiveawayHasWinner(id)){
            model.addAttribute("hasWinner",true);
        }
        if(!this.giveawayService.checkIfThereAreParticipantsInAGiveaway(id)){
            model.addAttribute("NoParticipants",true);
        }
        model.addAttribute("isFinished", this.giveawayService.checkIfIsFinished(id));

        String username= request.getRemoteUser();
        Boolean isCreator=this.giveawayService.checkIfUserIsCreator(id,username);
        Giveaway giveaway=this.giveawayService.findById(id);
        model.addAttribute("giveaway",giveaway);
        model.addAttribute("isCreator", isCreator);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","details-giveaway");
        return "master-template";
    }

    @RequestMapping(value = "/choose-winner/{id}", method =RequestMethod.GET)
    public String ChooseWinner(@PathVariable Integer id, Model model,HttpServletRequest request){
        Giveaway giveaway=this.giveawayService.findById(id);
        String username= request.getRemoteUser();
        model.addAttribute("giveaway",giveaway);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","choose-winner");
        return "master-template";
    }

    @PostMapping("/add-participant/{id}")
    public String addParticipant(@PathVariable Integer id, HttpServletRequest request){
        String username= request.getRemoteUser();
        if(this.giveawayService.checkForParticipationInAGiveaway(id,username)){
            return "redirect:/giveaway/details/"+id+"?message=You have already set your participation for this giveaway";
        }
        else{
            this.giveawayService.addParticipant(id,username);
            return "redirect:/giveaway/details/"+id+"?message=You have successfully participate for this giveaway";
        }

    }

    @PostMapping("/choosewinner/{id}")
    public String chooseWinner(@PathVariable Integer id, HttpServletRequest request, Model model){
        if(!this.giveawayService.checkIfGiveawayHasWinner(id)){
            User user=this.giveawayService.winner(id);
            String username= request.getRemoteUser();
            model.addAttribute("winner",user);
            model.addAttribute("username", username);
            model.addAttribute("bodyContent","choose-winner");
            return "master-template";
        }
        else{
            return "details-giveaway";
        }

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
        List<Integer> proba=country;
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

    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=giveaways_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Giveaway> giveawayList = giveawayService.findAll();

        GiveawayPDFExporter exporter = new GiveawayPDFExporter(giveawayList);
        exporter.export(response);

    }







}
