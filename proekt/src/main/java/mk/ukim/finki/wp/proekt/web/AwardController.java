package mk.ukim.finki.wp.proekt.web;

import mk.ukim.finki.wp.proekt.model.Award;
import mk.ukim.finki.wp.proekt.model.Manufacturer;
import mk.ukim.finki.wp.proekt.model.User;
import mk.ukim.finki.wp.proekt.model.enumerations.AwardStatus;
import mk.ukim.finki.wp.proekt.model.enumerations.Role;
import mk.ukim.finki.wp.proekt.sevice.AwardService;
import mk.ukim.finki.wp.proekt.sevice.ManufacturerService;
import mk.ukim.finki.wp.proekt.sevice.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/awards")
public class AwardController {

    private final ManufacturerService manufacturerService;
    private final AwardService awardService;
    private final UserService userService;

    public AwardController(ManufacturerService manufacturerService, AwardService awardService, UserService userService) {
        this.manufacturerService = manufacturerService;
        this.awardService = awardService;
        this.userService = userService;
    }

    @GetMapping
    public String getAwardsPage(Model model, HttpServletRequest req){
        String username=req.getRemoteUser();
        User user=this.userService.findByUsername(username);
        List<Award> awards=new ArrayList<Award>();
        if(user.getRole()== Role.ROLE_USER){
            awards=awardService.findAllByCreator(username);
        }
        else {
            awards=awardService.findAll();
        }
        AwardStatus status=AwardStatus.DEACTIVE;
        model.addAttribute("awards", awards);
        model.addAttribute("status", status);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","my-awards");
        return "master-template";

    }

    @GetMapping("/add-award")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String addAwardPage(Model model,HttpServletRequest req){
        String username=req.getRemoteUser();
        List<Manufacturer> manufacturers=this.manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","add-award");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveAward(@RequestParam String name,
                            @RequestParam Float weight,
                            @RequestParam String url,
                            @RequestParam Integer manufacturer,
                            HttpServletRequest req){

        String username=req.getRemoteUser();
        this.awardService.save(name, weight, url, manufacturer, username);
        return "redirect:/awards";
    }

    @PostMapping("/add/{id}")
    public String editAward(@RequestParam String name,
                            @RequestParam Float weight,
                            @RequestParam String url,
                            @RequestParam Integer manufacturer,
                            HttpServletRequest req, @PathVariable Integer id){
        String username=req.getRemoteUser();
        this.awardService.update(id,name,weight,url,manufacturer,username);
        return "redirect:/awards";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditPage(@PathVariable Integer id, Model model,HttpServletRequest req){
        Award award=this.awardService.findById(id);
        String username=req.getRemoteUser();
        List<Manufacturer> manufacturers=this.manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("award", award);
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","add-award");
        return "master-template";
    }

    @PostMapping("/delete/{id}")
    public String deleteAward(@PathVariable Integer id){
        this.awardService.deleteById(id);
        return "redirect:/awards";
    }

}
