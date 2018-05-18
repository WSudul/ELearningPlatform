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
    private String content;
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
        this.content = (content);
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
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lesson lesson = (Lesson) o;

        if (content != null ? !content.equals(lesson.content) : lesson.content != null) return false;
        if (idLesson != null ? !idLesson.equals(lesson.idLesson) : lesson.idLesson != null) return false;
        if (name != null ? !name.equals(lesson.name) : lesson.name != null) return false;
        return course != null ? course.equals(lesson.course) : lesson.course == null;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (idLesson != null ? idLesson.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (course != null ? course.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "content='" + content + '\'' +
                ", idLesson=" + idLesson +
                ", name='" + name + '\'' +
                ", course=" + course +
                '}';
    }
}
