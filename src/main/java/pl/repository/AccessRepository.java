package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.model.Access;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {


}

