package pl.wybankuj.service;

import org.springframework.stereotype.Service;
import pl.wybankuj.entity.Agency;
import pl.wybankuj.repository.AgencyRepository;
import pl.wybankuj.repository.BankRepository;

import java.util.Optional;

@Service
public class AgencyService {

    private final AgencyRepository agencyRepository;
    private final BankRepository bankRepository;

    public AgencyService(AgencyRepository agencyRepository, BankRepository bankRepository) {
        this.agencyRepository = agencyRepository;
        this.bankRepository = bankRepository;
    }

    public Iterable<Agency> findAllAgencies(Long bankId) {
        return agencyRepository.findAllByBankId(bankId);
    }

    public Optional<Agency> findAgencyById(Long agencyId) {
        return agencyRepository.findById(agencyId);
    }

}
