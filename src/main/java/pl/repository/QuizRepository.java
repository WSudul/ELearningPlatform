package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.model.Course;
import pl.model.Quiz;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // public List<Quiz> findBySubject(Course subject);


    List<Quiz> findBySubject(Course subject);

}
