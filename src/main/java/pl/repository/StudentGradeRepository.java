package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.model.Course;
import pl.model.StudentGrade;

import java.util.List;

@Repository
public interface StudentGradeRepository extends JpaRepository<StudentGrade, Long> {

    List<StudentGrade> findAllGradesByUseridAndSubject(Long userid, Course subject);
}
