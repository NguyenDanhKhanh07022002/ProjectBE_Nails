package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Message;
import com.bezkoder.springjwt.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/create")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
       ResponseEntity<Message> newMessage = messageService.createMessage(message);
        return ResponseEntity.ok(newMessage.getBody());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Message>> getAllMessage () {
        return ResponseEntity.ok(messageService.getAllMessages());
    }
}
