package pl.model;

public class Message {
    private Long idmessage;
    private String content;
    private String fromwho;
    private String title;
    private String towho;

    public Long getIdmessage() {
        return idmessage;
    }

    public void setIdmessage(Long idmessage) {
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
