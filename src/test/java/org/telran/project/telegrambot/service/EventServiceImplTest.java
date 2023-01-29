package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telran.project.telegrambot.model.*;
import org.telran.project.telegrambot.repository.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class EventServiceImplTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserChannelService userChannelService;

    @Autowired
    private UserChannelRepository userChannelRepository;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        messageRepository.deleteAll();
        userChannelRepository.deleteAll();
        channelRepository.deleteAll();
        eventRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createNewEvents() {
        User user = userRepository.save(new User("user"));
        Channel channel = channelRepository.save(new Channel("channel", 123));
        UserChannel userChannel = userChannelRepository.save(new UserChannel(user.getId(), channel.getId()));
        Message message = new Message("channel", 123, "text");
        messageRepository.save(message);
        List<Event> emptyEventList = eventRepository.findAll();
        assertEquals(0, emptyEventList.size());
        eventService.createNewEvents();
        List<Event> oneEventList = eventRepository.findAll();
        assertEquals(1, oneEventList.size());
        Event event = oneEventList.get(0);
        assertEquals("Hi! You have new message", event.getText());
    }

    @Test
    void getNewEventsWithExistingUserId() {
        User user = userRepository.save(new User("user"));
        Event event = new Event("text", user.getId(), 1);
        Event event2 = new Event("text", user.getId(), 2);
        Event event3 = new Event("text", 467, 3);
        eventRepository.save(event);
        eventRepository.save(event2);
        eventRepository.save(event3);
        List<Event> allEvents = eventRepository.findAll();
        assertEquals(3, allEvents.size());
        List<Event> newEventsByUserId = eventService.getNewEventsByUserId(user.getId());
        assertEquals(2, newEventsByUserId.size());
        List<Event> emptyEventList = eventService.getNewEventsByUserId(user.getId());
        assertEquals(0, emptyEventList.size());
    }

    @Test
    void getNewEventsWithWrongUserId() {
        Event event = new Event("text", 1, 1);
        Event event2 = new Event("text", 5, 2);
        Event event3 = new Event("text", 467, 3);
        eventRepository.save(event);
        eventRepository.save(event2);
        eventRepository.save(event3);
        List<Event> allEvents = eventRepository.findAll();
        assertEquals(3, allEvents.size());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> eventService.getNewEventsByUserId(5555));
        assertEquals("User with ID 5555 not found", exception.getMessage());
    }

    @Test
    void listAllEventsFromService() {
        List<Event> allFromRepository = eventRepository.findAll();
        List<Event> allFromService = eventService.listAll();
        assertEquals(allFromRepository, allFromService);
        Event event = new Event("text", 1, 1);
        Event event2 = new Event("text", 1, 2);
        Event event3 = new Event("text", 1, 3);
        eventRepository.save(event);
        eventRepository.save(event2);
        eventRepository.save(event3);
        List<Event> eventListAfterSave = eventService.listAll();
        assertEquals(3, eventListAfterSave.size());
    }
}