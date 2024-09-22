package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Booking;
import com.bezkoder.springjwt.models.Message;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MessageService {
    ResponseEntity<Message> createMessage(Message message);

    Message updateMessage(Long id, Message message);
    Message getMessageById(Long id);
    void deleteMessage(Long id);
    List<Message> getAllMessages();

    List<Message> findByEmail(String email);
}
