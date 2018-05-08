package pl.model;

import javax.persistence.*;

@Entity
@Table(name = "studentgrade")
public class StudentGrade {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "idstudentgrade")
  private Long idstudentgrade;

  @Column(name = "userid")
  private Long userid;

  @Column(name = "grade")
  private Double grade;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "subject_id")
  private Course course;

  @Column(name = "comment")
  private String comment;

  public StudentGrade() {
  }


  public StudentGrade(Long idstudentgrade, Long userid, Double grade, Course course) {
    super();
    this.userid = userid;
    this.grade = grade;
    this.course = course;
  }

  public Long getIdstudentgrade() {
    return idstudentgrade;
  }

  public void setIdstudentgrade(Long idstudentgrade) {
    this.idstudentgrade = idstudentgrade;
  }

  public Long getUserid() {
    return userid;
  }

  public void setUserid(Long userid) {
    this.userid = userid;
  }

  public Double getGrade() {
    return grade;
  }

  public void setGrade(Double grade) {
    this.grade = grade;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }


}