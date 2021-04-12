package mk.ukim.finki.wp.proekt.web;


import mk.ukim.finki.wp.proekt.model.*;
import mk.ukim.finki.wp.proekt.model.enumerations.AwardStatus;
import mk.ukim.finki.wp.proekt.model.enumerations.UserType;
import mk.ukim.finki.wp.proekt.sevice.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    private final UserService userService;
    private final GiveawayService giveawayService;
    private final RegionService regionService;
    private final AwardService awardService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    public HomeController(UserService userService, GiveawayService giveawayService, RegionService regionService, AwardService awardService, CategoryService categoryService, ManufacturerService manufacturerService) {
        this.userService = userService;
        this.giveawayService = giveawayService;
        this.regionService = regionService;
        this.awardService = awardService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }


    @GetMapping
    public String getHomePage(){
        return "Home";
    }


    @GetMapping("my-profile")
    public String getMyProfilePage(Model model, HttpServletRequest request){
        String username=request.getRemoteUser();
        User user=this.userService.findByUsername(username);
        List<Giveaway> activeGiveawayList = this.giveawayService.myActiveGiveaways(username);
        List<Giveaway> finishedGiveawayList = this.giveawayService.myFinishedGiveaways(username);
        List<Giveaway> giveawaysWaitingForWinner = this.giveawayService.myGiveawaysWaitingForWinner(username);
        model.addAttribute("user",user);
        model.addAttribute("activeGiveawayList",activeGiveawayList);
        model.addAttribute("finishedGiveawayList",finishedGiveawayList);
        model.addAttribute("giveawaysWaitingForWinner",giveawaysWaitingForWinner);


        return "my-profile";
    }
}
