package pl.model;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idmessage")
    private Long idmessage;
    @NotEmpty
    @Column(name = "title")
    private String title;
    @Column(name = "fromwho")
    private String fromwho;
    @Column(name = "towho")
    private String towho;
    @NotEmpty
    @Column(length = 50000)
    @Type(type = "text")
    private String content;

    public Message() {
    }

    public Message(String title, String from, String to, String content) {
        super();
        this.title = title;
        this.fromwho = from;
        this.towho = to;
        this.content = content;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Long getIdmessage() {
        return idmessage;
    }

    public void setIdmessage(Long idmessage) {
        this.idmessage = idmessage;
    }

    public String getFrom() {
        return fromwho;
    }

    public void setFrom(String from) {
        this.fromwho = from;
    }

    public String getTo() {
        return towho;
    }

    public void setTo(String to) {
        this.towho = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
