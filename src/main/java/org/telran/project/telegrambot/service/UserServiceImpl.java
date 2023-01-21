package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.User;
import org.telran.project.telegrambot.repository.UserRepository;

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
    public void createUser(User user) {
        if (!userRepository.existsByUserId(user.getUserId())) {
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(int id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    @Override
    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public User getUserByUserId(long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public void createUser(String name, long userId) {
        if (!userRepository.existsByUserId(userId)) {
            userRepository.save(new User(name, userId));
        }
    }
}
