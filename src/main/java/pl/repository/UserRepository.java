package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByFirstNameContaining(String firstName);

    List<User> findByLastNameContaining(String firstName);


}