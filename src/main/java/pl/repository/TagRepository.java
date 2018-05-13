package pl.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {


}