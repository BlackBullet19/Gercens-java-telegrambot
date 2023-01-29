package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telran.project.telegrambot.model.Statistic;
import org.telran.project.telegrambot.model.User;
import org.telran.project.telegrambot.model.UserRole;
import org.telran.project.telegrambot.repository.EventRepository;
import org.telran.project.telegrambot.repository.StatisticRepository;
import org.telran.project.telegrambot.repository.UserRepository;

import javax.ws.rs.NotAcceptableException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class StatisticServiceImplTest {

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
        userRepository.deleteAll();
        statisticRepository.deleteAll();
    }

    @Test
    void getAllStatisticWithRightId() {
        User user = userRepository.save(new User("user"));
        userRepository.changeUserStatus(UserRole.ADMIN, user.getId());
        User admin = userService.getUser(user.getId());
        statisticRepository.save(new Statistic("text", 1));
        List<Statistic> allStatistic = statisticService.getAllStatistic(admin.getId());
        assertEquals(1, allStatistic.size());
    }

    @Test
    void getAllStatisticWithExistingIdButItHaveNoAccess() {
        User user = userRepository.save(new User("user"));
        assertEquals(UserRole.USER, user.getStatus());
        statisticRepository.save(new Statistic("text", 1));
        NotAcceptableException exception = assertThrows(NotAcceptableException.class,
                () -> statisticService.getAllStatistic(user.getId()));
        assertEquals("User with ID " + user.getId() + " have no access", exception.getMessage());
    }

    @Test
    void getAllStatisticWithNonExistingId() {
        statisticRepository.save(new Statistic("text", 1));
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> statisticService.getAllStatistic(1));
        assertEquals("User with ID 1 not found", exception.getMessage());
    }

    @Test
    void createNewStatisticWithRightId() {
        User user = userRepository.save(new User("user"));
        userRepository.changeUserStatus(UserRole.ADMIN, user.getId());
        User admin = userService.getUser(user.getId());
        Statistic statistic = statisticService.createNewStatistic(admin.getId());
        assertEquals(0, statistic.getLastIndexOfLastEvent());
    }

    @Test
    void createNewStatisticWithExistingIdButItHaveNoAccess() {
        User user = userRepository.save(new User("user"));
        assertEquals(UserRole.USER, user.getStatus());
        NotAcceptableException exception = assertThrows(NotAcceptableException.class,
                () -> statisticService.getAllStatistic(user.getId()));
        assertEquals("User with ID " + user.getId() + " have no access", exception.getMessage());
    }

    @Test
    void createNewStatisticWithNonExistingId() {
        User user = userRepository.save(new User("user"));
        assertEquals(UserRole.USER, user.getStatus());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> statisticService.getAllStatistic(1));
        assertEquals("User with ID 1 not found", exception.getMessage());
    }
}