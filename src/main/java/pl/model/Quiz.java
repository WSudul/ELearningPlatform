package pl.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "quiz")
public class Quiz {

    private long idQuiz;
    private String name;
    private long subjectId;


    public long getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(long idQuiz) {
        this.idQuiz = idQuiz;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

}
