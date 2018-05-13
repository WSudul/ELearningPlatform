package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.model.Course;
import pl.model.CourseGrade;

import java.util.List;

@Repository
public interface CourseGradeRepository extends JpaRepository<CourseGrade, Long> {

    List<CourseGrade> findAllByUseridAndCourse(Long userid, Course subject);

    List<CourseGrade> findAllBySubject(Course subject);
}
