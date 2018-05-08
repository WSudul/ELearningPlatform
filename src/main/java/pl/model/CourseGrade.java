package pl.model;

import javax.persistence.*;

@Entity
@Table(name = "coursegrade")
public class CourseGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idcoursegrade")
    private Long idcoursegrade;

    @Column(name = "userid")
    private Long userid;


    @Column(name = "grade")
    private Integer grade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Course course;

    public CourseGrade() {
    }

    public CourseGrade(Long userid, int grade, Course course) {
        super();
        this.userid = userid;
        this.grade = grade;
        this.course = course;
    }

    public Long getIdcoursegrade() {
        return idcoursegrade;
    }

    public void setIdcoursegrade(Long idcoursegrade) {
        this.idcoursegrade = idcoursegrade;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
