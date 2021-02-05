package pl.wybankuj.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wybankuj.entity.Bank;
import pl.wybankuj.service.BankService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @RequestMapping(
            value = "/bank",
            produces = "application/json",
            method = {RequestMethod.GET})
    public ResponseEntity<List<Bank>> allBanks() {
        try {
            List<Bank> banks = new ArrayList<>();
            bankService.findAllBanks().forEach(banks::add);

            if (banks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(banks, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(
            value = "/bank/{id}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public ResponseEntity<Bank> getBankById(@PathVariable("id") Long id) {
        Optional<Bank> bankData = bankService.findBankById(id);

        return bankData.map(bank -> new ResponseEntity<>(bank, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(
            value = "/bank/add",
            produces = "application/json",
            method = {RequestMethod.POST})
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        try {
            Bank bankToSave = bankService.addBank(new Bank(bank.getBankName(), bank.getLogo()));
            return new ResponseEntity<>(bankToSave, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(
            value = "/bank/{id}",
            produces = "application/json",
            method = {RequestMethod.PUT})
    public ResponseEntity<Bank> updateBank(@PathVariable("id") long id, @RequestBody Bank bank) {
        Optional<Bank> bankData = bankService.findBankById(id);

        if (bankData.isPresent()) {
            Bank bankToEdit = bankData.get();
            bankToEdit.setBankName(bank.getBankName());
            bankToEdit.setLogo(bank.getLogo());
            return new ResponseEntity<>(bankService.editBank2(bankToEdit), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/bank/{id}",
            produces = "application/json",
            method = {RequestMethod.DELETE})
    public ResponseEntity<HttpStatus> deleteBank(@PathVariable("id") Long id) {
        try {
            bankService.deleteBank(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}