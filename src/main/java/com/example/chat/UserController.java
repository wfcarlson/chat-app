package com.example.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody User user) {
        user.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            userRepository.findByName(user.getName()).orElseThrow(
                    () -> new UserNotFoundException("User with username " + user.getName() + " not found")
            );
        }
        catch (UserNotFoundException ex) {
            userRepository.save(user);
            user.setPassword(null);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user with username \"" + user.getName() + "\" already exists");

    }

    @GetMapping
    public User getUser(@RequestBody User user) throws IncorrectLoginException {
        return userRepository.findByName(user.getName()).orElseThrow(
                () -> new IncorrectLoginException("invalid name")
        );
    }

}
