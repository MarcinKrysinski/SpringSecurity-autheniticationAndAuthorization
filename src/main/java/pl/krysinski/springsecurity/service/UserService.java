package pl.krysinski.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.krysinski.springsecurity.entity.AppUser;
import pl.krysinski.springsecurity.entity.VerificationToken;
import pl.krysinski.springsecurity.repository.AppUserRepo;
import pl.krysinski.springsecurity.repository.VerificationTokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;
    private AppUserRepo appUserRepo;
    private VerificationTokenRepository verificationTokenRepository;
    private MailSenderService mailSenderService;

    public UserService(PasswordEncoder passwordEncoder, AppUserRepo appUserRepo, VerificationTokenRepository verificationTokenRepository, MailSenderService mailSenderService) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepo = appUserRepo;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailSenderService = mailSenderService;
    }

    public void addNewUser(AppUser appUser, HttpServletRequest request) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUserRepo.save(appUser);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(appUser, token);
        verificationTokenRepository.save(verificationToken);

        String url = "http://" + request.getServerName() + ":" +
                request.getServerPort() +
                request.getContextPath() +
                "/verify-token?token=" +
                token;

        mailSenderService.send(appUser.getUsername(), "Verification token", url);
    }

    public void verifyToken(String token){
        AppUser appUser = verificationTokenRepository.findByValue(token).getAppUser();
        appUser.setIsEnabled(true);
        appUserRepo.save(appUser);
    }
}
