package com.example.chat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity(name="user")
@Table(name="users")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Timestamp timeCreated;

    private String password;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Chat> chats;

    public User() {

    }

    public User(String name, Timestamp timeCreated, String password) {
        this.name = name;
        this.timeCreated = timeCreated;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Chat> getChats() {
        return chats;
    }
}
