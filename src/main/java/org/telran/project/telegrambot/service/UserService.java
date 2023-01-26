package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.User;

import java.util.List;

public interface UserService {

    List<User> list();

    User createUser(User user);

    void deleteUser(int id);

    User getUser(int id);

    User getUserByUserId(long userId);

    User createUser(String name, long userId);

    User createUserWithoutSavingToRepository(String name, long userId);

    List<User> saveAllUsers(List<User> list);

    boolean existsById(int id);

    User updateUser(int id, User user);
}
