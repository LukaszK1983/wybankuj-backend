package pl.wybankuj.web;

import org.springframework.web.bind.annotation.*;
import pl.wybankuj.entity.User;
import pl.wybankuj.repository.AgencyRepository;
import pl.wybankuj.repository.BankRepository;
import pl.wybankuj.repository.UserRepository;
import pl.wybankuj.service.EmailService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class HomeAgencyController {

    private final EmailService emailService;
    private final AgencyRepository agencyRepository;
    private final BankRepository bankRepository;
    private final UserRepository userRepository;

    public HomeAgencyController(EmailService emailService, AgencyRepository agencyRepository, BankRepository bankRepository, UserRepository userRepository) {
        this.emailService = emailService;
        this.agencyRepository = agencyRepository;
        this.bankRepository = bankRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(
            value = "/loanAgencyContactFormSend/{bankId}/{offer}/{amount}/{creditPeriod}/{age}/{chooseServiceCharge}/{chooseInsurance}/{name}/{email}/{phone}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public void sendAgencyContactForm(@PathVariable String name,
                                      @PathVariable String email, @PathVariable int amount,
                                      @PathVariable int creditPeriod, @PathVariable String offer,
                                      @PathVariable int age, @PathVariable String chooseServiceCharge,
                                      @PathVariable String chooseInsurance, @PathVariable String phone,
                                      @PathVariable Long bankId) {

        String title = "Wiadomość z Wybankuj.pl - " + name;
        String agencyBank = bankRepository.findFirstById(bankId).get().getBankName();
        String title2 = "Nowe zapytanie o kredyt gotówkowy: " + agencyBank + " - " + name + "";

        emailService.send("bank@wybankuj.pl", title, "gotówkowy", offer, amount, creditPeriod, phone, email, name);
        emailService.send("wnioski@wybankuj.pl", title2, "gotówkowy", offer, amount, creditPeriod, phone, email, name);

        String title3 = "Wiadomość z Wybankuj.pl";
        emailService.sendToUser(email, title3, agencyBank);

        userRepository.save(new User(LocalDateTime.now(), name, phone, email, agencyBank, "gotówkowy", offer, amount, creditPeriod));
    }

    @RequestMapping(
            value = "/mortgageAgencyContactFormSend/{bankId}/{offer}/{amount}/{creditPeriod}/{age}/{chooseServiceCharge}/{chooseInsurance}/{contributionPercent}/{cost}/{name}/{email}/{phone}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public void sendMortgageAgencyContactForm(@PathVariable String name,
                                              @PathVariable String email, @PathVariable int amount,
                                              @PathVariable int creditPeriod, @PathVariable String offer,
                                              @PathVariable int age, @PathVariable String chooseServiceCharge,
                                              @PathVariable String chooseInsurance, @PathVariable BigDecimal contributionPercent,
                                              @PathVariable BigDecimal cost, @PathVariable String phone,
                                              @PathVariable Long bankId) {

        String title = "Wiadomość z Wybankuj.pl - " + name;
        String agencyBank = bankRepository.findFirstById(bankId).get().getBankName();
        String title2 = "Nowe zapytanie o kredyt hipoteczny: " + agencyBank + " - " + name + "";

        emailService.send("bank@wybankuj.pl", title, "hipoteczny", offer, amount, creditPeriod, phone, email, name);
        emailService.send("wnioski@wybankuj.pl", title2, "hipoteczny", offer, amount, creditPeriod, phone, email, name);

        String title3 = "Wiadomość z Wybankuj.pl";
        emailService.sendToUser(email, title3, agencyBank);

        userRepository.save(new User(LocalDateTime.now(), name, phone, email, agencyBank, "hipoteczny", offer, amount, creditPeriod));
    }
}
