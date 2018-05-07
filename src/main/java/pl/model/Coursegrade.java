package pl.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "coursegrade")
public class Coursegrade {

    private long idcoursegrade;
    private long grade;
    private long userid;
    private long subjectId;


    public long getIdcoursegrade() {
        return idcoursegrade;
    }

    public void setIdcoursegrade(long idcoursegrade) {
        this.idcoursegrade = idcoursegrade;
    }


    public long getGrade() {
        return grade;
    }

    public void setGrade(long grade) {
        this.grade = grade;
    }


    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }


    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

}
