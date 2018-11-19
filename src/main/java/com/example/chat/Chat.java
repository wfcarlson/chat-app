package com.example.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name="chat")
@Table(name="chat")
public class Chat implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="users_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User users;

    private String message;

    private Timestamp timestamp;

    public Chat() {

    }

    public Chat(User user, String message) {
        this.users = user;
        this.message = message;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public User getUser() {
        return users;
    }

    public void setUser(User user) {
        this.users = user;
    }

    @Override
    public String toString() {
        try {
            return "Chat: " + timestamp.toString() + ", from: " + users.getName() + ", message: " + message;

        }
        catch (NullPointerException ex) {
            return "Chat: " + timestamp.toString() + ", message: " + message;
        }
    }
}
