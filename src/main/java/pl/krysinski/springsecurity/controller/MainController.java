package pl.krysinski.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.krysinski.springsecurity.entity.AppUser;
import pl.krysinski.springsecurity.repository.AppUserRepo;
import pl.krysinski.springsecurity.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {


    private UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/signup")
    public String signUp(Model model){
        model.addAttribute("user", new AppUser());
        return "registration";
    }

    @RequestMapping("/register")
    public String registration(@ModelAttribute AppUser appUser, HttpServletRequest request){
        userService.addNewUser(appUser, request);
        return "redirect:/login";
    }

    @RequestMapping("/verify-token")
    public String verifyToken(@RequestParam String token){
        userService.verifyToken(token);
        return "redirect:/login";
    }
}
