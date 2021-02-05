package pl.wybankuj.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wybankuj.entity.Agency;
import pl.wybankuj.entity.Bank;
import pl.wybankuj.repository.AgencyRepository;
import pl.wybankuj.service.BankService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class AgencyController {

    private final AgencyRepository agencyRepository;
    private final BankService bankService;

    public AgencyController(AgencyRepository agencyRepository, BankService bankService) {
        this.agencyRepository = agencyRepository;
        this.bankService = bankService;
    }

    @RequestMapping(
            value = "/agencies/{bankId}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public ResponseEntity<List<Agency>> allAgencies(@PathVariable("bankId") Long bankId) {
        try {
            List<Agency> agencies = new ArrayList<>();
            agencyRepository.findAllByBankId(bankId).forEach(agencies::add);

            if (agencies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(agencies, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(
            value = "/agency/{id}",
            produces = "application/json",
            method = {RequestMethod.GET})
    public ResponseEntity<Agency> getAgencyById(@PathVariable("id") Long id) {
        Optional<Agency> agencyData = agencyRepository.findById(id);

        return agencyData.map(agency -> new ResponseEntity<>(agency, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(
            value = "/agency/add",
            produces = "application/json",
            method = {RequestMethod.POST})
    public ResponseEntity<Agency> createAgency(@RequestBody Agency agency) {
        try {
            Agency agencyToSave = agencyRepository.save(agency);
            return new ResponseEntity<>(agencyToSave, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(
            value = "/agency/{id}",
            produces = "application/json",
            method = {RequestMethod.PUT})
    public ResponseEntity<Agency> updateAgency(@PathVariable("id") long id, @RequestBody Agency agency) {
        Optional<Agency> agencyData = agencyRepository.findById(id);

        if (agencyData.isPresent()) {
            Agency agencyToEdit = agencyData.get();
            agencyToEdit.setAgencyName(agency.getAgencyName());
            agencyToEdit.setStreet(agency.getStreet());
            agencyToEdit.setStreetNumber(agency.getStreetNumber());
            agencyToEdit.setZipCode(agency.getZipCode());
            agencyToEdit.setCity(agency.getCity());
            agencyToEdit.setPhone(agency.getPhone());
            agencyToEdit.setEmail(agency.getEmail());
            agencyToEdit.setHours(agency.getHours());
            return new ResponseEntity<>(agencyRepository.save(agencyToEdit), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/agencies/{id}",
            produces = "application/json",
            method = {RequestMethod.DELETE})
    public ResponseEntity<HttpStatus> deleteAgency(@PathVariable("id") Long id) {
        try {
            agencyRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ModelAttribute("hours")
    public List<String> hours() {
        return Arrays.asList("08:00 - 18:00", "09:00 - 17:00", "09:30 - 17:30", "10:00 - 18:00");
    }

    @ModelAttribute("banks")
    public List<Bank> banks() {
        return (List<Bank>) bankService.findAllBanks();
    }
}
