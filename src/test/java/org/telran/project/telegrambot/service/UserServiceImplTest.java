package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telran.project.telegrambot.model.User;
import org.telran.project.telegrambot.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void getListOfAllUsersFromUserService() {
        List<User> emptyList = userRepository.findAll();
        assertEquals(0, emptyList.size());
        List<User> emptyListFromService = userService.list();
        assertEquals(0, emptyListFromService.size());
        User user = new User("user");
        User user1 = new User("user1");
        User user2 = new User("user2");
        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> listAfterSave = userService.list();
        assertEquals(3, listAfterSave.size());
    }

    @Test
    void createUserWithAllNonEmptyFields() {
        List<User> all = userRepository.findAll();
        assertEquals(0, all.size());
        User user = new User("UserFour");
        userService.createUser(user);
        List<User> all1 = userRepository.findAll();
        assertEquals(1, all1.size());
        User userFromDataBase = all1.get(0);
        assertEquals("UserFour", userFromDataBase.getName());
    }

    @Test
    void createUserWithEmptyNameField() {
        List<User> all = userRepository.findAll();
        assertEquals(0, all.size());
        User user = new User(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(user));
        assertEquals("At least one entered parameter was 0 or empty", exception.getMessage());
        List<User> all1 = userRepository.findAll();
        assertEquals(0, all1.size());
    }

    @Test
    void createUserWithSameFieldAsOneUserExist() {
        List<User> all = userRepository.findAll();
        assertEquals(0, all.size());
        User user = new User("UserFive");
        User user1 = new User("UserFive");
        userService.createUser(user);
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> userService.createUser(user1));
        assertEquals("User with entered parameters already exists", exception.getMessage());
        List<User> all1 = userRepository.findAll();
        assertEquals(1, all1.size());
    }

    @Test
    void deleteUserWithRightId() {
        User user = new User("user");
        User user1 = new User("user1");
        User user2 = new User("user2");
        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> threeUserList = userRepository.findAll();
        assertEquals(3, threeUserList.size());
        userService.deleteUser(user.getId());
        List<User> twoUserList = userRepository.findAll();
        assertEquals(2, twoUserList.size());
        assertFalse(userRepository.existsByName(user.getName()));
    }

    @Test
    void deleteUserWithWrongId() {
        User user = new User("user");
        User user1 = new User("user1");
        User user2 = new User("user2");
        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> threeUserList = userRepository.findAll();
        assertEquals(3, threeUserList.size());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userService.deleteUser(555));
        assertEquals("User with ID 555 not found", exception.getMessage());
        List<User> threeUserListAfterDelete = userRepository.findAll();
        assertEquals(3, threeUserListAfterDelete.size());
    }

    @Test
    void deleteUserWithNullId() {
        User user = new User("user");
        User user1 = new User("user1");
        User user2 = new User("user2");
        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> threeUserList = userRepository.findAll();
        assertEquals(3, threeUserList.size());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.deleteUser(0));
        assertEquals("Entered value can not be 0", exception.getMessage());
        List<User> threeUserListAfterDelete = userRepository.findAll();
        assertEquals(3, threeUserListAfterDelete.size());
    }

    @Test
    void getUserWithRightId() {
        List<User> all = userRepository.findAll();
        assertEquals(0, all.size());
        User user = new User("UserFour");
        userRepository.save(user);
        User savedUser = userService.getUser(user.getId());
        assertEquals("UserFour", savedUser.getName());
    }

    @Test
    void getUserWithNullId() {
        List<User> all = userRepository.findAll();
        assertEquals(0, all.size());
        User user = new User("UserFour");
        userRepository.save(user);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.getUser(0));
        assertEquals("Entered value can not be 0", exception.getMessage());
    }

    @Test
    void getUserWithNotExistingId() {
        List<User> all = userRepository.findAll();
        assertEquals(0, all.size());
        User user = new User("UserFour");
        userRepository.save(user);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userService.getUser(555));
        assertEquals("User with ID 555 not found", exception.getMessage());
    }

    @Test
    void updateUserWithAllNonEmptyFields() {
        User user = userService.createUser(new User("UserFour"));
        List<User> all1 = userRepository.findAll();
        assertEquals(1, all1.size());
        User updateUser = userService.updateUser(user.getId(), new User("UserFive"));
        List<User> all = userRepository.findAll();
        assertEquals(1, all.size());
        User updatedUser = userRepository.findById(user.getId()).get();
        assertEquals(updatedUser.getId(), user.getId());
    }

    @Test
    void updateUserWithAllNonEmptyFieldsButNotExistingId() {
        User user = userService.createUser(new User("UserFour"));
        List<User> allBefore = userRepository.findAll();
        assertEquals(1, allBefore.size());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userService.updateUser(5, new User("UserFive")));
        assertEquals("User with ID 5 not found", exception.getMessage());
        List<User> allAfter = userRepository.findAll();
        assertEquals(1, allAfter.size());
        assertEquals("UserFour", allAfter.get(0).getName());
    }

    @Test
    void updateUserWithAllNullId() {
        User user = userService.createUser(new User("UserFour"));
        List<User> allBefore = userRepository.findAll();
        assertEquals(1, allBefore.size());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(0, new User("UserFive")));
        assertEquals("Entered value can not be 0", exception.getMessage());
        List<User> allAfter = userRepository.findAll();
        assertEquals(1, allAfter.size());
        assertEquals("UserFour", allAfter.get(0).getName());
    }

    @Test
    void existsWithExistingId() {
        List<User> all = userRepository.findAll();
        assertEquals(0, all.size());
        User user = new User("UserFour");
        userRepository.save(user);
        assertTrue(userService.existsById(user.getId()));
    }

    @Test
    void existsWithNotExistingId() {
        List<User> all = userRepository.findAll();
        assertEquals(0, all.size());
        User user = new User("UserFour");
        userRepository.save(user);
        assertFalse(userService.existsById(555));
    }

    @Test
    void existsWithNullId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.existsById(0));
        assertEquals("Entered value can not be 0", exception.getMessage());
    }
}