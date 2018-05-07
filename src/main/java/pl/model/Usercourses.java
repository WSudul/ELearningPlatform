package pl.model;


public class Usercourses {

    private long id;
    private long completed;
    private java.sql.Timestamp dateCompleted;
    private long userId;
    private long courseId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getCompleted() {
        return completed;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }


    public java.sql.Timestamp getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(java.sql.Timestamp dateCompleted) {
        this.dateCompleted = dateCompleted;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

}
