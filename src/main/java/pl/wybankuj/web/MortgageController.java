package pl.wybankuj.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wybankuj.entity.Bank;
import pl.wybankuj.entity.Mortgage;
import pl.wybankuj.repository.MortgageRepository;
import pl.wybankuj.service.BankService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class MortgageController {

    private final MortgageRepository mortgageRepository;
    private final BankService bankService;

    public MortgageController(MortgageRepository mortgageRepository, BankService bankService) {
        this.mortgageRepository = mortgageRepository;
        this.bankService = bankService;
    }

    @RequestMapping(
            value = "/mortgages/{bankId}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public ResponseEntity<List<Mortgage>> allMortgages(@PathVariable("bankId") Long bankId) {
        try {
            List<Mortgage> mortgages = new ArrayList<>(mortgageRepository.findAllByBankId(bankId));

            if (mortgages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(mortgages, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(
            value = "/mortgage/{id}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public ResponseEntity<Mortgage> getMortgageById(@PathVariable("id") Long id) {
        Optional<Mortgage> mortgageData = mortgageRepository.findById(id);

        return mortgageData.map(mortgage -> new ResponseEntity<>(mortgage, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(
            value = "/mortgage/add",
            produces = "application/json",
            method = {RequestMethod.POST})
    public ResponseEntity<Mortgage> createMortgage(@RequestBody Mortgage mortgage) {
        try {
            Mortgage mortgageToSave = mortgageRepository.save(mortgage);
            return new ResponseEntity<>(mortgageToSave, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(
            value = "/mortgage/{id}",
            produces = "application/json",
            method = {RequestMethod.PUT})
    public ResponseEntity<Mortgage> updateLoan(@PathVariable("id") long id, @RequestBody Mortgage mortgage) {
        Optional<Mortgage> mortgageData = mortgageRepository.findById(id);

        if (mortgageData.isPresent()) {
            Mortgage mortgageToEdit = mortgageData.get();
            mortgageToEdit.setOffer(mortgage.getOffer());
            mortgageToEdit.setCreditRate(mortgage.getCreditRate());
            mortgageToEdit.setServiceCharge(mortgage.getServiceCharge());
            mortgageToEdit.setInsurance(mortgage.getInsurance());
            mortgageToEdit.setContributionPercent(mortgage.getContributionPercent());
            mortgageToEdit.setMinCreditAmount(mortgage.getMinCreditAmount());
            mortgageToEdit.setMaxCreditAmount(mortgage.getMaxCreditAmount());
            mortgageToEdit.setMinBorrowerAge(mortgage.getMinBorrowerAge());
            mortgageToEdit.setMaxBorrowerAge(mortgage.getMaxBorrowerAge());
            mortgageToEdit.setMaxCreditPeriod(mortgage.getMaxCreditPeriod());
            return new ResponseEntity<>(mortgageRepository.save(mortgageToEdit), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/mortgages/{id}",
            produces = "application/json",
            method = {RequestMethod.DELETE})
    public ResponseEntity<HttpStatus> deleteMortgage(@PathVariable("id") Long id) {
        try {
            mortgageRepository.deleteById(id);
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
