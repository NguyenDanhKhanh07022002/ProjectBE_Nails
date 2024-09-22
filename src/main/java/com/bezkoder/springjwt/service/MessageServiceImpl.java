package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Message;
import com.bezkoder.springjwt.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public ResponseEntity<Message> createMessage(Message message) {
        message.setCreateAt(String.valueOf(System.currentTimeMillis()));
        Message createMessage = messageRepository.save(message);
        return ResponseEntity.ok(createMessage);
    }

    @Override
    public Message updateMessage(Long id, Message message) {
        return null;
    }

    @Override
    public Message getMessageById(Long id) {
        return null;
    }

    @Override
    public void deleteMessage(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            Message existingMessage = message.get();
            existingMessage.setDeleted(true);
            messageRepository.save(existingMessage);
        } else {
            throw new RuntimeException("Message not found with id: " + id);
        }
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        List<Message> activeMessage = new ArrayList<>();
        for (Message message : messages) {
            if (!message.isDeleted()) {
                activeMessage.add(message);
            }
        }
        return activeMessage;
    }

    @Override
    public List<Message> findByEmail(String email) {
        return null;
    }
}
