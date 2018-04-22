package pl.model;

public class Lesson {
    private Long id_lesson;
    private String content;
    private String name;
    private Long subject_id;

    public Long getId_lesson() {
        return id_lesson;
    }

    public void setId_lesson(Long id_lesson) {
        this.id_lesson = id_lesson;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Long subject_id) {
        this.subject_id = subject_id;
    }
}
