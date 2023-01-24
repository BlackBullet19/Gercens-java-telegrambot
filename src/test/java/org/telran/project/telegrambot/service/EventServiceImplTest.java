package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telran.project.telegrambot.model.*;
import org.telran.project.telegrambot.repository.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventServiceImplTest {

    @Autowired
    private ChannelRepository channelRepository;
    private ChannelService channelService;

    @Autowired
    private EventRepository eventRepository;
    private EventService eventService;

    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;


    @Autowired
    private UserChannelRepository userChannelRepository;
    private UserChannelService userChannelService;

    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        channelService = new ChannelServiceImpl(channelRepository);
        messageService = new MessageServiceImpl(messageRepository);
        userChannelService = new UserChannelServiceImpl(userChannelRepository);
        userService = new UserServiceImpl(userRepository);
        eventService= new EventServiceImpl(messageService, userChannelService, userService, channelService, eventRepository);
    }

    @AfterEach
    void tearDown() {
        channelRepository.deleteAll();
        eventRepository.deleteAll();
        messageRepository.deleteAll();
        userChannelRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void canCreateNewEvents() {
        Message message = new Message(10, "title", 100, "testText");
        messageRepository.save(message);

        User user = new User("userOne" , 999);
        userRepository.save(user);
        Channel channel = new Channel("title", 100);
        channelRepository.save(channel);
        int id = userRepository.findByUserId(999).getId();
        UserChannel userChannel = new UserChannel(id, 100);
        userChannelRepository.save(userChannel);

        eventService.createNewEvents();
        List<Event> newEventsByUserId = eventService.getNewEventsByUserId(id);
        String text = "Hello, userOne in group - 'title' you have new messages!";
        assertEquals(text, newEventsByUserId.get(0).getText());
    }

    @Test
    void canGetNewEventsByUserId() {
        eventRepository.save(new Event("text", 1));

        List<Event> newEventsByUserId = eventService.getNewEventsByUserId(1);
        assertEquals("text",newEventsByUserId.get(0).getText() );
    }

    @Test
    void canCreateEvent() {
        eventService.createEvent(12, "textTest");
        List<Event> newEventsByUserId = eventService.getNewEventsByUserId(12);
        assertEquals("textTest", newEventsByUserId.get(0).getText());
    }
}