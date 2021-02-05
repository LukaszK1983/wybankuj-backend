package pl.wybankuj.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wybankuj.entity.Mortgage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MortgageRepository extends CrudRepository<Mortgage, Long> {

    Optional<Mortgage> findById(Long id);

    List<Mortgage> findAllByBankId(Long bankId);

    @Query("SELECT m FROM Mortgage m WHERE m.minCreditAmount <= :amount AND m.maxCreditAmount >= :amount AND m.maxCreditPeriod >= :creditPeriod AND m.minBorrowerAge <= :age AND m.maxBorrowerAge >= :age AND m.contributionPercent <= :contributionPercent AND m.maxBorrowerAge - :age >= :creditPeriod / 12")
    List<Mortgage> findAllByParameters(int amount, int creditPeriod, int age, BigDecimal contributionPercent);

    @Query("SELECT m FROM Mortgage m WHERE m.minCreditAmount <= :amount AND m.maxCreditAmount >= :amount AND m.maxCreditPeriod >= :creditPeriod AND m.minBorrowerAge <= :age AND m.maxBorrowerAge >= :age AND m.insurance = 0.0 AND m.contributionPercent <= :contributionPercent AND m.maxBorrowerAge - :age >= :creditPeriod / 12")
    List<Mortgage> findAllByParametersWithoutInsurance(int amount, int creditPeriod, int age, BigDecimal contributionPercent);

    @Query("SELECT m FROM Mortgage m WHERE m.minCreditAmount <= :amount AND m.maxCreditAmount >= :amount AND m.maxCreditPeriod >= :creditPeriod AND m.minBorrowerAge <= :age AND m.maxBorrowerAge >= :age AND m.serviceCharge = 0.0 AND m.contributionPercent <= :contributionPercent AND m.maxBorrowerAge - :age >= :creditPeriod / 12")
    List<Mortgage> findAllByParametersWithoutServiceCharge(int amount, int creditPeriod, int age, BigDecimal contributionPercent);

    @Query("SELECT m FROM Mortgage m WHERE m.minCreditAmount <= :amount AND m.maxCreditAmount >= :amount AND m.maxCreditPeriod >= :creditPeriod AND m.minBorrowerAge <= :age AND m.maxBorrowerAge >= :age AND m.insurance = 0.0 AND m.serviceCharge = 0.0 AND m.contributionPercent <= :contributionPercent AND m.maxBorrowerAge - :age >= :creditPeriod / 12")
    List<Mortgage> findAllByParametersWithoutInsuranceAndServiceCharge(int amount, int creditPeriod, int age, BigDecimal contributionPercent);
}
