package com.example.chat;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.chat.security.SecurityConstants.SECRET;
import static com.example.chat.security.SecurityConstants.TOKEN_PREFIX;

@Service
public class AuthenticatedUserService implements IAuthenticatedUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getAuthenticatedUser(String token) throws UserNotFoundException {
        String username = (String) Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody().get("sub");
        return userRepository.findByName(username).orElseThrow(() -> new UserNotFoundException("user with name " + username + " not found."));
    }

}
