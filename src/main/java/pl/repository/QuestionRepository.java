package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
