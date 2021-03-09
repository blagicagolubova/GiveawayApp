package mk.ukim.finki.wp.proekt.web;


import mk.ukim.finki.wp.proekt.model.User;
import mk.ukim.finki.wp.proekt.sevice.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getHomePage(){
        return "Home";
    }

    @GetMapping("my-profile")
    public String getMyProfilePage(Model model, HttpServletRequest request){
        String username=request.getRemoteUser();
        User user=this.userService.findByUsername(username);
        model.addAttribute("user",user);
        return "my-profile";
    }
}
