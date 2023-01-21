package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.User;

import java.util.List;

public interface UserService {

    List<User> list();

    void createUser(User user);

    void deleteUser(int id);

    User getUser(int id);

    User getUserByUsername(String username);

    User getUserByUserId(long userId);

    void createUser(String name, long userId);
}
