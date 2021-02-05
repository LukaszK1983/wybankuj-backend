package pl.wybankuj.web;

import org.springframework.web.bind.annotation.*;
import pl.wybankuj.entity.*;
import pl.wybankuj.repository.MortgageRepository;
import pl.wybankuj.service.MortgageService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class HomeMortgageController {

    private final MortgageRepository mortgageRepository;
    private final MortgageService mortgageService;

    public HomeMortgageController(MortgageRepository mortgageRepository, MortgageService mortgageService) {
        this.mortgageRepository = mortgageRepository;
        this.mortgageService = mortgageService;
    }

    @RequestMapping(
            value = "/calculateMortgage/{amount}/{creditPeriod}/{age}/{chooseServiceCharge}/{chooseInsurance}/{contributionPercent}/{cost}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public List<MortgageWithPayment> allMortgageParameters(@PathVariable("amount") int amount,
                                                           @PathVariable("creditPeriod") int creditPeriod,
                                                           @PathVariable("age") int age,
                                                           @PathVariable("chooseServiceCharge") String chooseServiceCharge,
                                                           @PathVariable("chooseInsurance") String chooseInsurance,
                                                           @PathVariable("contributionPercent") BigDecimal contributionPercent,
                                                           @PathVariable("cost") BigDecimal cost) {

        try {
            List<Mortgage> mortgages = mortgageService.chooseOffers(chooseInsurance,
                    chooseServiceCharge, amount, creditPeriod, age, contributionPercent);
            List<MortgageWithPayment> mortgageWithPayments = mortgageService.calculateMortgagePayment(mortgages, amount, creditPeriod);

            if (mortgageWithPayments.isEmpty()) {
                return null;
            }

            return mortgageWithPayments;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(
            value = "/mortgageDetails/{mortgageId}/{amount}/{creditPeriod}/{age}/{chooseServiceCharge}/{chooseInsurance}/{contributionPercent}/{cost}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public List<CalculationsMortgage> getLoanDetails(@PathVariable("mortgageId") Long mortgageId,
                                                     @PathVariable("amount") int amount,
                                                     @PathVariable("creditPeriod") int creditPeriod,
                                                     @PathVariable("age") int age,
                                                     @PathVariable("chooseServiceCharge") String chooseServiceCharge,
                                                     @PathVariable("chooseInsurance") String chooseInsurance,
                                                     @PathVariable("contributionPercent") BigDecimal contributionPercent,
                                                     @PathVariable("cost") BigDecimal cost) {

        List<CalculationsMortgage> calculationsMortgageList = new ArrayList<>();

        CalculationsMortgage calculationsMortgage = calculateMortgageParameteres(mortgageId, amount, creditPeriod, age, chooseServiceCharge, chooseInsurance, contributionPercent);

        calculationsMortgageList.add(calculationsMortgage);

        return calculationsMortgageList;
    }

    @ModelAttribute("answears")
    public List<String> answears() {
        return List.of("TAK", "NIE");
    }

    public CalculationsMortgage calculateMortgageParameteres(Long mortgageId, int amount, int creditPeriod, int age, String chooseServiceCharge, String chooseInsurance, BigDecimal contributionPercent) {
        Mortgage mortgage = mortgageRepository.findById(mortgageId).orElse(new Mortgage());

        BigDecimal payment = mortgageService.calculateChoosenMortgagePayment(mortgage, amount, creditPeriod);

        BigDecimal serviceCharge = mortgageService.calculateServiceCharge(mortgage, amount);

        BigDecimal insurance = mortgageService.calculateInsurance(mortgage, amount);

        BigDecimal interests = mortgageService.calculateInterestsCost(mortgage, amount, creditPeriod, payment, serviceCharge, insurance);

        BigDecimal totalCost = mortgageService.calculateTotalCost(mortgage, amount, creditPeriod, payment);

        return new CalculationsMortgage(mortgage, payment, serviceCharge, insurance, interests, totalCost, mortgage.getBank().getLogo(), mortgage.getBank().getId());
    }
}
