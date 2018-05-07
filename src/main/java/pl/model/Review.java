package pl.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "review")
public class Review {

    private long id;
    private String text;
    private long dateAdded;
    private long score;
    private long usercoursesId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }


    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }


    public long getUsercoursesId() {
        return usercoursesId;
    }

    public void setUsercoursesId(long usercoursesId) {
        this.usercoursesId = usercoursesId;
    }

}
