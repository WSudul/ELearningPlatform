package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.model.Course;
import pl.model.StudentGrade;

import java.util.List;

public interface StudentGradeRepository extends JpaRepository<StudentGrade, Long> {

    List<StudentGrade> findAllGradesByUseridAndSubject(Long userid, Course subject);
}
