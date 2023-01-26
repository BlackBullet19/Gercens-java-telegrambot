package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.User;
import org.telran.project.telegrambot.repository.UserRepository;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        if (user.getUserId() == 0) {
            throw new IllegalArgumentException("Parameters are not valid");
        }
        if (userRepository.existsByUserId(user.getUserId())) {
            throw new IllegalStateException("User already exists");
        }
        User newUser = new User(user.getName(), user.getUserId());
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid identifier");
        }
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
        }
    }

    @Override
    public User getUser(int id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).get();
        }
        throw new IllegalArgumentException("Invalid identifier");
    }

    @Override
    public User getUserByUserId(long userId) {
        if (userRepository.existsByUserId(userId)) {
            return userRepository.findByUserId(userId);
        }
        throw new IllegalArgumentException("Invalid userId");
    }

    @Override
    public User createUser(String name, long userId) {
        if (userId == 0) {
            throw new IllegalArgumentException("Parameters are not valid");
        }
        if (userRepository.existsByUserId(userId)) {
            throw new IllegalStateException("User already exists");
        }
        User newUser = new User(name, userId);
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(int id, User user) {
        if (userRepository.existsById(id)) {
            User existingUser = userRepository.findById(id).get();
            if (user != null) {
                existingUser.setUserId(user.getUserId());
                existingUser.setName(user.getName());
            }
            return userRepository.save(existingUser);
        }
        throw new InvalidParameterException("Parameters are not valid");
    }

    @Override
    public User createUserWithoutSavingToRepository(String name, long userId) {
        if (userId != 0) {
            return new User(name, userId);
        }
        throw new IllegalArgumentException("userId is not valid");
    }

    @Override
    public List<User> saveAllUsers(List<User> list) {
        return userRepository.saveAll(list);
    }

    @Override
    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }
}
