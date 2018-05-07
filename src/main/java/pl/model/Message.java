package pl.model;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message {

    private long idmessage;
    private String content;
    private String fromwho;
    private String title;
    private String towho;


    public long getIdmessage() {
        return idmessage;
    }

    public void setIdmessage(long idmessage) {
        this.idmessage = idmessage;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getFromwho() {
        return fromwho;
    }

    public void setFromwho(String fromwho) {
        this.fromwho = fromwho;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTowho() {
        return towho;
    }

    public void setTowho(String towho) {
        this.towho = towho;
    }

}
