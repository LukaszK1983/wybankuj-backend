package pl.wybankuj.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wybankuj.entity.Agency;

import java.util.List;

@Repository
public interface AgencyRepository extends CrudRepository<Agency, Long> {

    List<Agency> findAllByBankId(Long bankId);

    List<Agency> findAllByBankIdAndCity(Long bankId, String city);
}
