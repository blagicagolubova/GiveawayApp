package mk.ukim.finki.wp.proekt.web;

import mk.ukim.finki.wp.proekt.model.exceptions.InvalidArgumentException;
import mk.ukim.finki.wp.proekt.model.exceptions.PasswordDoNotMatchException;
import mk.ukim.finki.wp.proekt.sevice.AuthService;
import mk.ukim.finki.wp.proekt.sevice.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    private final AuthService authService;

    public RegisterController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("username", null);
        model.addAttribute("bodyContent","register");
        return "master-template";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String address

    ) {
        try{
            this.userService.save(username, password, repeatedPassword, name, surname, address, phone, email);
            return "redirect:/login";
        } catch (InvalidArgumentException | PasswordDoNotMatchException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}
