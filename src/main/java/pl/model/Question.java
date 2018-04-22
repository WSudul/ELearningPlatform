package pl.model;

public class Question {
    private Long id_question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private Long correct_answer;
    private String question;
    private Long id_quiz;

    public Long getId_question() {
        return id_question;
    }

    public void setId_question(Long id_question) {
        this.id_question = id_question;
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

    public Long getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(Long correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getId_quiz() {
        return id_quiz;
    }

    public void setId_quiz(Long id_quiz) {
        this.id_quiz = id_quiz;
    }
}
