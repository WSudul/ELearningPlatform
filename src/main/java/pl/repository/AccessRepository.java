package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.model.Access;
import pl.model.UserRole;

import java.util.Optional;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {
    Optional<UserRole> findByRole(String role);


}

