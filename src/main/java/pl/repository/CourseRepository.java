package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    

}
