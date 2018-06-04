package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.model.Usercourses;


@Repository
public interface UserCoursesRepository extends JpaRepository<Usercourses, Long> {
}