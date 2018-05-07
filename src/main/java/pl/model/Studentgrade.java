package pl.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "studentgrade")
public class Studentgrade {

  private long idstudentgrade;
  private String comment;
  private double grade;
  private long userid;
  private long subjectId;


  public long getIdstudentgrade() {
    return idstudentgrade;
  }

  public void setIdstudentgrade(long idstudentgrade) {
    this.idstudentgrade = idstudentgrade;
  }


  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }


  public double getGrade() {
    return grade;
  }

  public void setGrade(double grade) {
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
