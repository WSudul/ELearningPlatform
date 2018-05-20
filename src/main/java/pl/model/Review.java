package pl.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String text;
    private Date dateAdded;
    private long score;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usercourses_id")
    private Usercourses usercourses;


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


    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }


    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }


    public Usercourses getUsercourses() {
        return usercourses;
    }

    public void setUsercourses(Usercourses usercourses) {
        this.usercourses = usercourses;
    }
}
