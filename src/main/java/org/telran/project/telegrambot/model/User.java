package org.telran.project.telegrambot.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * User entity is main entity we'll use everywhere concerning user
 *
 * @author Olegs Gercens
 * @version 1.0
 */
@Entity
@Table(name = "bot_user")
public class User {

    /**
     * User identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Username
     */
    @NotBlank
    private String name;

    /**
     * Users role here, can be USER or ADMIN, by default when created is USER
     */
    @Enumerated(EnumType.STRING)
    private UserRole status;

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

    public UserRole getStatus() {
        return status;
    }

    public void setStatus(UserRole status) {
        this.status = status;
    }

    public User(String name) {
        this.name = name;
        this.status = UserRole.USER;
    }

    public User() {
        //
    }
}
