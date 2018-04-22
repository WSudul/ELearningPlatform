package pl.model;

public class Coursegrade {
    private Long idcoursegrade;
    private Long grade;
    private Long userid;
    private Long subject_id;

    public Long getIdcoursegrade() {
        return idcoursegrade;
    }

    public void setIdcoursegrade(Long idcoursegrade) {
        this.idcoursegrade = idcoursegrade;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
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
