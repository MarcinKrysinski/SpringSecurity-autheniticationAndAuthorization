package pl.krysinski.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.krysinski.springsecurity.entity.AppUser;
import pl.krysinski.springsecurity.repository.AppUserRepo;

@Component
public class StartUser {

    private PasswordEncoder passwordEncoder;
    private AppUserRepo appUserRepo;

    @Autowired
    public StartUser(PasswordEncoder passwordEncoder, AppUserRepo appUserRepo) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepo = appUserRepo;

        AppUser appUser = new AppUser();
        appUser.setUsername("Admin");
        appUser.setPassword(passwordEncoder.encode("Admin123"));
        appUserRepo.save(appUser);
    }
}
