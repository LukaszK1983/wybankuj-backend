package pl.wybankuj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wybankuj.entity.Bank;

import java.util.Optional;

@Repository
//public interface BankRepository extends CrudRepository<Bank, Long> {
public interface BankRepository extends JpaRepository<Bank, Long> {

    Optional<Bank> findFirstById(Long bankId);
}
