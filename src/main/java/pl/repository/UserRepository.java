package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByFirstNameContaining(String firstName);

    List<User> findByLastNameContaining(String firstName);

    @Modifying
    @Transactional
    @Query("UPDATE User SET email = ?1 WHERE email = ?2")
    public int changeEmail(String newEmail, String oldEmail);

}