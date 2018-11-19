package com.example.chat;


public interface IAuthenticatedUserService {

    public User getAuthenticatedUser(String token) throws UserNotFoundException;

}
