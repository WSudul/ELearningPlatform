package pl.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface EmailSender {
    void sendEmail(String from, String to, String title, String content);
}
