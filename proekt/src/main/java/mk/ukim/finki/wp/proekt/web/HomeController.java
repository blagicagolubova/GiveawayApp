package mk.ukim.finki.wp.proekt.web;


import mk.ukim.finki.wp.proekt.model.*;
import mk.ukim.finki.wp.proekt.model.enumerations.AwardStatus;
import mk.ukim.finki.wp.proekt.model.enumerations.UserType;
import mk.ukim.finki.wp.proekt.sevice.*;
import mk.ukim.finki.wp.proekt.views.Top3;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getHomePage(Model model, HttpServletRequest request){
        String username=request.getRemoteUser();
        List<Giveaway> giveawayList=this.giveawayService.top3AvailableForParticipation(username);
        model.addAttribute("giveawayList", giveawayList);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","Home");
        return "master-template";
    }


    @GetMapping("my-profile")
    public String getMyProfilePage(Model model, HttpServletRequest request){
        String username=request.getRemoteUser();
        User user=this.userService.findByUsername(username);
        List<Giveaway> activeGiveawayList = this.giveawayService.myActiveGiveaways(username);
        List<Giveaway> finishedGiveawayList = this.giveawayService.myFinishedGiveaways(username);
        List<Giveaway> giveawaysWaitingForWinner = this.giveawayService.myGiveawaysWaitingForWinner(username);
        model.addAttribute("user",user);
        model.addAttribute("editUser",false);
        model.addAttribute("activeGiveawayList",activeGiveawayList);
        model.addAttribute("finishedGiveawayList",finishedGiveawayList);
        model.addAttribute("giveawaysWaitingForWinner",giveawaysWaitingForWinner);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","my-profile");
        return "master-template";
    }

    @GetMapping("my-profile/editbutton")
    public String buttonEdit(Model model, HttpServletRequest request){
        model.addAttribute("editUser",true);
        String username=request.getRemoteUser();
        User user=this.userService.findByUsername(username);
        List<Giveaway> activeGiveawayList = this.giveawayService.myActiveGiveaways(username);
        List<Giveaway> finishedGiveawayList = this.giveawayService.myFinishedGiveaways(username);
        List<Giveaway> giveawaysWaitingForWinner = this.giveawayService.myGiveawaysWaitingForWinner(username);
        model.addAttribute("user",user);
        model.addAttribute("activeGiveawayList",activeGiveawayList);
        model.addAttribute("finishedGiveawayList",finishedGiveawayList);
        model.addAttribute("giveawaysWaitingForWinner",giveawaysWaitingForWinner);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","my-profile");
        return "master-template";
    }

    @PostMapping("my-profile/edit")
    public String editProfilePage(@RequestParam String name,
                                  @RequestParam String surname,
                                  @RequestParam String address,
                                  @RequestParam String email,
                                  @RequestParam String phone,
                                  Model model,
                                  HttpServletRequest request){
        String username=request.getRemoteUser();
        this.userService.update(username,name,surname,address,phone,email);
        model.addAttribute("editUser",false);
        User user=this.userService.findByUsername(username);
        List<Giveaway> activeGiveawayList = this.giveawayService.myActiveGiveaways(username);
        List<Giveaway> finishedGiveawayList = this.giveawayService.myFinishedGiveaways(username);
        List<Giveaway> giveawaysWaitingForWinner = this.giveawayService.myGiveawaysWaitingForWinner(username);
        model.addAttribute("user",user);
        model.addAttribute("activeGiveawayList",activeGiveawayList);
        model.addAttribute("finishedGiveawayList",finishedGiveawayList);
        model.addAttribute("giveawaysWaitingForWinner",giveawaysWaitingForWinner);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","my-profile");
        return "master-template";
    }

    @GetMapping("/access_denied")
    public String getAccessDeniedPage(Model model) {
        model.addAttribute("bodyContent", "access_denied");
        return "master-template";
    }



}
