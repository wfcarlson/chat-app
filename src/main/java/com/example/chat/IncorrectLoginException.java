package com.example.chat;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IncorrectLoginException extends Exception {
    private static final long serialVersionUID = 1L;
    public IncorrectLoginException(String message){
        super(message);
    }
}