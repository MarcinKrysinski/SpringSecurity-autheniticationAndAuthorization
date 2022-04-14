package pl.krysinski.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.krysinski.springsecurity.entity.AppUser;
import pl.krysinski.springsecurity.repository.AppUserRepo;

@Controller
public class MainController {

    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MainController(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
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
    public String registration(@ModelAttribute AppUser appUser, Model model){
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        model.addAttribute("user", appUserRepo.save(appUser));
        return "redirect:/login";
    }
}
