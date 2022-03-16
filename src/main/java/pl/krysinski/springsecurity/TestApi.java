package pl.krysinski.springsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestApi {

    @GetMapping("/logout")
    public String logout(){
        return "You have been logout";
    }


    @GetMapping("/forAll")
    public String forAll(Principal principal){
        return "Hello user: " + principal.getName();
    }

    @GetMapping("/forUser")
    public String forUser(Principal principal){
        return "Hello user: " + principal.getName();
    }

    @GetMapping("/forAdmin")
    public String forAdmin(Principal principal){
        return "Hello admin: " + principal.getName();
    }
}
