package mk.ukim.finki.wp.proekt.web;

import mk.ukim.finki.wp.proekt.model.enumerations.UserType;
import mk.ukim.finki.wp.proekt.sevice.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }


    @GetMapping("/add-manu")
    public String addManufacturerPage(Model model,HttpServletRequest req){
        String username=req.getRemoteUser();
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","add-manu");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveManufacturer(@RequestParam String name,
                                   @RequestParam(required = false) String description,
                                   HttpServletRequest request)
    {
        this.manufacturerService.save(name, description);
        return "redirect:/awards/add-award";

    }


}
