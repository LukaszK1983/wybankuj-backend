package pl.wybankuj.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import pl.wybankuj.entity.Calculations;
import pl.wybankuj.entity.Loan;
import pl.wybankuj.entity.LoanWithPayment;
import pl.wybankuj.repository.LoanRepository;
import pl.wybankuj.service.LoanService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class HomeLoanController {

    private final static Logger logger = LogManager.getLogger(HomeLoanController.class);

    private final LoanRepository loanRepository;
    private final LoanService loanService;

    public HomeLoanController(LoanRepository loanRepository, LoanService loanService) {
        this.loanRepository = loanRepository;
        this.loanService = loanService;
    }

    @RequestMapping(
            value = "/calculateLoan/{amount}/{creditPeriod}/{age}/{chooseServiceCharge}/{chooseInsurance}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public List<LoanWithPayment> allLoanParameters(@PathVariable("amount") int amount,
                                                   @PathVariable("creditPeriod") int creditPeriod,
                                                   @PathVariable("age") int age,
                                                   @PathVariable("chooseServiceCharge") String chooseServiceCharge,
                                                   @PathVariable("chooseInsurance") String chooseInsurance) {

        try {
            List<Loan> loans = loanService.chooseOffers(chooseInsurance,
                    chooseServiceCharge, amount, creditPeriod, age);
            List<LoanWithPayment> loansWithPayments = loanService.calculateLoanPayment(loans, amount, creditPeriod, age);

            if (loansWithPayments.isEmpty()) {
                return null;
            }

            return loansWithPayments;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(
            value = "/loanDetails/{loanId}/{amount}/{creditPeriod}/{age}/{chooseServiceCharge}/{chooseInsurance}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public List<Calculations> getLoanDetails(@PathVariable("loanId") Long loanId,
                                       @PathVariable("amount") int amount,
                                       @PathVariable("creditPeriod") int creditPeriod,
                                       @PathVariable("age") int age,
                                       @PathVariable("chooseServiceCharge") String chooseServiceCharge,
                                       @PathVariable("chooseInsurance") String chooseInsurance
    ) {

        List<Calculations> calculationsList = new ArrayList<>();

        Calculations calculations = calculateLoanParameteres(loanId, amount, creditPeriod, age, chooseServiceCharge, chooseInsurance);

        calculationsList.add(calculations);

        return calculationsList;
    }

    @ModelAttribute("answears")
    public List<String> answears() {
        return List.of("TAK", "NIE");
    }

    public Calculations calculateLoanParameteres(Long loanId, int amount, int creditPeriod, int age, String chooseServiceCharge, String chooseInsurance) {

        Loan loan = loanRepository.findById(loanId).orElse(new Loan());

        BigDecimal payment = loanService.calculateChoosenLoanPayment(loan, amount, creditPeriod);

        BigDecimal serviceCharge = loanService.calculateServiceCharge(loan, amount);

        BigDecimal insurance = loanService.calculateInsurance(loan, amount);

        BigDecimal interests = loanService.calculateInterestsCost(amount, creditPeriod, payment, serviceCharge, insurance);

        BigDecimal totalCost = loanService.calculateTotalCost(amount, creditPeriod, payment);

        Calculations calculation = new Calculations(loan, payment, serviceCharge, insurance, interests, totalCost, loan.getBank().getLogo(), loan.getBank().getId());

        return calculation;
    }
}
