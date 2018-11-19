package com.example.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Timestamp;

import static com.example.chat.security.SecurityConstants.HEADER_STRING;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

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
    public User getUser(@RequestHeader(value=HEADER_STRING) String token) throws Exception {
        return authenticatedUserService.getAuthenticatedUser(token);
    }

    @GetMapping("/sent")
    public ResponseEntity getSent(@RequestHeader(value=HEADER_STRING) String token) throws UserNotFoundException {
        User user = authenticatedUserService.getAuthenticatedUser(token);
        return ResponseEntity.ok(user.getChats());
    }

}
