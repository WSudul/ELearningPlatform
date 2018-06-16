package pl.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.model.Tag;

import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {


    Set<Tag> findAllByNameInIgnoreCase(Set<String> names);
}