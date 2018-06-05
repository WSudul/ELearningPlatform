package pl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.model.Message;
import pl.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    public void sendMessage(Message message) {
        messageRepository.save(message);
    }

    public List<Message> findAllMessages(String towho) {
        return messageRepository.findAllByTowho(towho);
    }

    public Optional<Message> findMessageById(long id) {
        return messageRepository.findOneById(id);
    }
}
