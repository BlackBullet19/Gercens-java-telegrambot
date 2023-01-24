package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telran.project.telegrambot.model.User;
import org.telran.project.telegrambot.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserServiceImplTest {

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void canGetListOfUser() {
        userRepository.save(new User("userOne", 11));
        userRepository.save(new User("userTwo", 12));
        userRepository.save(new User("userThree", 13));
        List<User> list = userService.list();
        assertEquals(3, list.size());
    }

    @Test
    void canCreateUserWithObjectUser() {
        User user = new User("UserFour", 14);
        userService.createUser(user);
        User userByUserId = userService.getUserByUserId(14);
        assertEquals(user, userByUserId);
    }

    @Test
    void canDeleteUser() {
        userRepository.save(new User("userOne", 11));
        userRepository.save(new User("userTwo", 12));
        userRepository.save(new User("userThree", 13));
        User userByUserId = userService.getUserByUserId(11);
        userService.deleteUser(userByUserId.getId());
        List<User> list = userService.list();
        assertEquals(2, list.size());
    }

    @Test
    void canGetUserByUserIntID() {
        User userOne = new User("userOne", 14);
        userService.createUser(userOne);
        User userByUserId = userService.getUserByUserId(14);

        User user = userService.getUser(userByUserId.getId());
        assertEquals(userOne, user);
    }

    @Test
    void canGetUserByUsername() {
        userRepository.save(new User("userOne", 11));
        userRepository.save(new User("userTwo", 12));
        userRepository.save(new User("userThree", 13));
        User userOne = userService.getUserByUsername("userOne");
        assertEquals(11, userOne.getUserId());
    }

    @Test
    void canGetUserByUserLongId() {
        userRepository.save(new User("userOne", 11));
        userRepository.save(new User("userTwo", 12));
        userRepository.save(new User("userThree", 13));
        User userByUserId = userService.getUserByUserId(12);
        assertEquals("userTwo", userByUserId.getName());
    }

    @Test
    void canCreateUserWithConstructor() {
        userService.createUser("userFour", 14);
        User userByUserId = userService.getUserByUserId(14);
        assertEquals("userFour", userByUserId.getName());

    }

    @Test
    void canCreateUserWithoutSavingToRepository() {

        User userOne = userService.createUserWithoutSavingToRepository("userOne", 1);
        assertEquals(1, userOne.getUserId());
    }

    @Test
    void canSaveAllUsersFromList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("userFour", 14));
        userList.add(new User("userFive", 15));
        userList.add(new User("userSix", 16));
        userService.saveAllUsers(userList);
        List<User> list = userService.list();
        assertEquals(3, list.size());
    }
}