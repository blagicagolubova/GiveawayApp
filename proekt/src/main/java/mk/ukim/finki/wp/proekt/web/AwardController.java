package mk.ukim.finki.wp.proekt.web;

import mk.ukim.finki.wp.proekt.model.Award;
import mk.ukim.finki.wp.proekt.model.Manufacturer;
import mk.ukim.finki.wp.proekt.sevice.AwardService;
import mk.ukim.finki.wp.proekt.sevice.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/awards")
public class AwardController {

    private final ManufacturerService manufacturerService;

    private final AwardService awardService;

    public AwardController(ManufacturerService manufacturerService, AwardService awardService) {
        this.manufacturerService = manufacturerService;
        this.awardService = awardService;
    }

    @GetMapping
    public String getAwardsPage(Model model, HttpServletRequest req){
        String username=req.getRemoteUser();
        List<Award> awards=awardService.findAllByCreator(username);
        model.addAttribute("awards", awards);
        return "my-awards";

    }

    @GetMapping("/add-award")
    public String addAwardPage(Model model){
        List<Manufacturer> manufacturers=this.manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);
        return "add-award";
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
    public String getEditPage(@PathVariable Integer id, Model model){
        Award award=this.awardService.findById(id);
        List<Manufacturer> manufacturers=this.manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("award", award);
        return "add-award";
    }

    @PostMapping("/delete/{id}")
    public String deleteAward(@PathVariable Integer id){
        this.awardService.deleteById(id);
        return "redirect:/awards";
    }

}
