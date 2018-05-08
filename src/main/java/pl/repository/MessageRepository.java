package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByTowho(String towho);


}
