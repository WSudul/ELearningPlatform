package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.model.Course;
import pl.model.User;
import pl.model.Usercourses;

import java.util.Optional;


@Repository
public interface UserCoursesRepository extends JpaRepository<Usercourses, Long> {
    Optional<Usercourses> findOneByCourseAndUser(Course course, User user);
}