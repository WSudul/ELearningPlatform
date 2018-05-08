package pl.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "lesson")
public class Lesson {
    @NotEmpty
    @Column(length = 50000)
    @Type(type = "text")
    private final ThreadLocal<String> content = new ThreadLocal<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lesson")
    private Long idLesson;
    @NotEmpty
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Course course;

    public Lesson() {
    }

    public Lesson(Long idLesson, String name, String content, Course course) {
        super();
        this.idLesson = idLesson;
        this.name = name;
        this.content.set(content);
        this.course = course;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getIdLesson() {
        return idLesson;
    }

    public void setIdLesson(Long idLesson) {
        this.idLesson = idLesson;
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
