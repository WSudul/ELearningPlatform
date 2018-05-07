package pl.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class Course {

    private long id;
    private String description;
    private String name;


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

}
