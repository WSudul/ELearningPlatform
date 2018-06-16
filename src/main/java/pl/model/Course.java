package pl.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    @Column(length = 1000)
    @Type(type = "text")
    private String description;
    @NotEmpty
    private String name;

    //@NotEmpty
    private String tags;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "course_tags",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tagSet = new HashSet<>();


    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Quiz> quizes = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CourseGrade> grades = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<StudentGrade> studentGrades = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    @LazyCollection(LazyCollectionOption.TRUE)
    private Set<Usercourses> usercourses = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Tag> getTagSet() {
        return tagSet;
    }

    public void setTagSet(Set<Tag> tagSet) {
        this.tagSet = tagSet;
    }

    public List<Quiz> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<Quiz> quizes) {
        this.quizes = quizes;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<CourseGrade> getGrades() {
        return grades;
    }

    public void setGrades(List<CourseGrade> grades) {
        this.grades = grades;
    }

    public List<StudentGrade> getStudentGrades() {
        return studentGrades;
    }

    public void setStudentGrades(List<StudentGrade> studentGrades) {
        this.studentGrades = studentGrades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (description != null ? !description.equals(course.description) : course.description != null) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (tagSet != null ? !tagSet.equals(course.tagSet) : course.tagSet != null) return false;
        if (quizes != null ? !quizes.equals(course.quizes) : course.quizes != null) return false;
        if (lessons != null ? !lessons.equals(course.lessons) : course.lessons != null) return false;
        if (grades != null ? !grades.equals(course.grades) : course.grades != null) return false;
        return studentGrades != null ? studentGrades.equals(course.studentGrades) : course.studentGrades == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tagSet != null ? tagSet.hashCode() : 0);
        result = 31 * result + (quizes != null ? quizes.hashCode() : 0);
        result = 31 * result + (lessons != null ? lessons.hashCode() : 0);
        result = 31 * result + (grades != null ? grades.hashCode() : 0);
        result = 31 * result + (studentGrades != null ? studentGrades.hashCode() : 0);
        return result;
    }

    public Set<Usercourses> getUsercourses() {
        return usercourses;
    }

    public void setUsercourses(Set<Usercourses> usercourses) {
        this.usercourses = usercourses;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", tagSet=" + tagSet.stream().map(Tag::getName).collect(Collectors.toList()) +
                '}';
    }
}
