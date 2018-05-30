package pl.service;

import pl.model.Course;
import pl.model.Quiz;
import pl.repository.QuestionRepository;
import pl.repository.QuizRepository;

import java.util.List;
import java.util.Optional;

public class QuizService {

    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;

    public void setQuizRepository(QuizRepository quizRepository) { this.quizRepository = quizRepository; }

    public void setQuestionRepository(QuestionRepository questionRepository) { this.questionRepository = questionRepository; }

    public Optional<Quiz> findQuizById(long idQuiz) {
        return Optional.of(quizRepository.getOne(idQuiz));
    }

    public List<Quiz> findQuizByCourse(Course course) {
        return quizRepository.findByCourse(course);
    }
}
