package com.example.chat;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.chat.security.SecurityConstants.HEADER_STRING;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    Logger logger = LoggerFactory.getLogger(ChatController.class);

    @PostMapping
    public ResponseEntity sendChat(@RequestBody Chat chat, @RequestHeader(value=HEADER_STRING) String token) throws UserNotFoundException {
        User user = authenticatedUserService.getAuthenticatedUser(token);
        chat.setUser(user);
        Chat userChat = new Chat(user, chat.getMessage());
        chatRepository.save(userChat);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getChats() {
        return ResponseEntity.ok(chatRepository.findAllByOrderByTimestampAsc());
    }
}
