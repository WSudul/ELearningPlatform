package pl.repository;

public interface EmailSender {
    void sendEmail(String from, String to, String title, String content);
}
