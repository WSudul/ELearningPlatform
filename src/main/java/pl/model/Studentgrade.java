package pl.model;

public class Studentgrade {
    private Long idstudentgrade;
    private String comment;
    private Double grade;
    private Long userid;
    private Long subject_id;

    public Long getIdstudentgrade() {
        return idstudentgrade;
    }

    public void setIdstudentgrade(Long idstudentgrade) {
        this.idstudentgrade = idstudentgrade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Long subject_id) {
        this.subject_id = subject_id;
    }
}
