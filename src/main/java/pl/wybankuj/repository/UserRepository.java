package pl.wybankuj.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wybankuj.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
