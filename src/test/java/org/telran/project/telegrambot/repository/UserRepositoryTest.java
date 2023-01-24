package org.telran.project.telegrambot.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telran.project.telegrambot.model.User;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(new User("userOne", 100));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void canFindByName() {
        User userOne = userRepository.findByName("userOne");
        assertEquals(100, userOne.getUserId());
    }

    @Test
    void WhenExistsByNameThenTrue() {
        boolean userOne = userRepository.existsByName("userOne");
        assertEquals(true, userOne);
    }

    @Test
    void WhenExistsByUserIdThenTrue() {
        boolean existsByUserId = userRepository.existsByUserId(100);
        assertEquals(true, existsByUserId);

    }

    @Test
    void canFindByUserId() {
        User byUserId = userRepository.findByUserId(100);
        assertEquals("userOne", byUserId.getName());

    }
}