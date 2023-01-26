package org.telran.project.telegrambot.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bot_user")
public class User {

    private static final String DEFAULT_USERNAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private long userId;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public User(String name, long userId) {
        this.name = name.isEmpty() ? DEFAULT_USERNAME : name;
        this.userId = userId;
    }

    public User() {
    }
}
