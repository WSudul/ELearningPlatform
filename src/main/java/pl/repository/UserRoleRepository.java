package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.model.UserRole;

import java.util.Optional;


public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRole(String role);

    Optional<UserRole> findById(Long id);
}