package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
