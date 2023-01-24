package org.telran.project.telegrambot.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telran.project.telegrambot.model.Event;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;


    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
    }

    @Test
    void canFindAllByUserId() {
        int userId = 1;
        eventRepository.save(new Event("text", userId));
        eventRepository.save(new Event("textTwo", userId));
        List<Event> eventList = eventRepository.findAllByUserId(userId);
        assertEquals(2, eventList.size());
    }
}