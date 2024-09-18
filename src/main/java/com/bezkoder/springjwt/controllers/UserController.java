package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody User request) {
       Long id = Long.valueOf(request.getId());
       String username = request.getUsername();
       String email = request.getEmail();
       String password = request.getPassword();

       User user = userRepository.findUsersById(id);
       BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       user.setUsername(username);
       user.setEmail(email);
       user.setPassword(passwordEncoder.encode(password));
       userRepository.save(user);
       return ResponseEntity.ok().build();
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserById (@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUsersById(id));
    }
}
