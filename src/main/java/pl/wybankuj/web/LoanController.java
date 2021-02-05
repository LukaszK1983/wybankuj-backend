package pl.wybankuj.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wybankuj.entity.Bank;
import pl.wybankuj.entity.Loan;
import pl.wybankuj.repository.LoanRepository;
import pl.wybankuj.service.BankService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class LoanController {

    private final static Logger logger = LogManager.getLogger(LoanController.class);

    private final LoanRepository loanRepository;
    private final BankService bankService;

    public LoanController(LoanRepository loanRepository, BankService bankService) {
        this.loanRepository = loanRepository;
        this.bankService = bankService;
    }

    @RequestMapping(
            value = "/loans/{bankId}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public ResponseEntity<List<Loan>> allLoans(@PathVariable("bankId") Long bankId) {
        try {
            List<Loan> loans = new ArrayList<>();
            loanRepository.findAllByBankId(bankId).forEach(loans::add);

            if (loans.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(loans, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(
            value = "/loan/{id}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public ResponseEntity<Loan> getLoanById(@PathVariable("id") Long id) {
        Optional<Loan> loanData = loanRepository.findById(id);

        return loanData.map(loan -> new ResponseEntity<>(loan, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(
            value = "/loan/add",
            produces = "application/json",
            method = {RequestMethod.POST})
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        try {
            Loan loanToSave = loanRepository.save(loan);
            return new ResponseEntity<>(loanToSave, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(
            value = "/loan/{id}",
            produces = "application/json",
            method = {RequestMethod.PUT})
    public ResponseEntity<Loan> updateLoan(@PathVariable("id") long id, @RequestBody Loan loan) {
        Optional<Loan> loanData = loanRepository.findById(id);

        if (loanData.isPresent()) {
            Loan loanToEdit = loanData.get();
            loanToEdit.setOffer(loan.getOffer());
            loanToEdit.setCreditRate(loan.getCreditRate());
            loanToEdit.setServiceCharge(loan.getServiceCharge());
            loanToEdit.setInsurance(loan.getInsurance());
            loanToEdit.setMinCreditAmount(loan.getMinCreditAmount());
            loanToEdit.setMaxCreditAmount(loan.getMaxCreditAmount());
            loanToEdit.setMinBorrowerAge(loan.getMinBorrowerAge());
            loanToEdit.setMaxBorrowerAge(loan.getMaxBorrowerAge());
            loanToEdit.setMaxCreditPeriod(loan.getMaxCreditPeriod());
            return new ResponseEntity<>(loanRepository.save(loanToEdit), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/loans/{id}",
            produces = "application/json",
            method = {RequestMethod.DELETE})
    public ResponseEntity<HttpStatus> deleteLoan(@PathVariable("id") Long id) {
        try {
            loanRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ModelAttribute("banks")
    public List<Bank> banks() {
        return (List<Bank>) bankService.findAllBanks();
    }
}
