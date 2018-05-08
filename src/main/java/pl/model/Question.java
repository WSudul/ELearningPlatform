package pl.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "question")
public class Question {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_question")
    private Long idQuestion;
    @NotEmpty
    @Column(name = "question")
    private String quest;
    @NotEmpty
    private String answer1;
    @NotEmpty
    private String answer2;
    @NotEmpty
    private String answer3;
    @NotEmpty
    private String answer4;

    private Long correct_answer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_quiz")
    private Quiz quiz;

    public Question() {
    }

    public Question(Long idQuestion, String quest, String answer1, String answer2, String answer3, String answer4,
                    Long correct_answer, Quiz quiz) {
        super();
        this.idQuestion = idQuestion;
        this.quest = quest;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correct_answer = correct_answer;
        this.quiz = quiz;
    }

    public Long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Long idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public Long getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(Long correct_answer) {
        this.correct_answer = correct_answer;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }


}
