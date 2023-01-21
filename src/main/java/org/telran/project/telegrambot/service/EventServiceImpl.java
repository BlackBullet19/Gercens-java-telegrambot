package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Event;
import org.telran.project.telegrambot.model.Message;
import org.telran.project.telegrambot.repository.EventRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserChannelService userChannelService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private EventRepository eventRepository;


    @Scheduled(fixedRate = 10000) // through properties
    @Override
    public void createNewEvents() {
        List<Message> messages = messageService.listAllNewMessages();
        messages.forEach(m -> messageService.changeIsNewToFalse(m.getId()));
        Set<Long> channelIds = new HashSet<>();
        for (Message m : messages) {
            channelIds.add(m.getChatId());
        }
        List<Long> listOfChannelIds = channelIds.stream().toList();
        for (Long ch : listOfChannelIds) {
            List<Integer> allSubscribedUserIdsByChannelId = userChannelService.findAllUsersByChannelId(ch);
            allSubscribedUserIdsByChannelId.forEach(u -> {
                String username = userService.getUser(u).getName();
                String channelName = channelService.getChannel(ch).getName();
                String text = "Hello, " + username + " in group - '"
                        + channelName + "' you have new messages!";
                createEvent(u, text);
            });
        }
    }

    @Override
    public List<Event> getNewEventsByUserId(int userId) {
        return eventRepository.findAllByUserId(userId);
    }

    @Override
    public void createEvent(int userId, String text) {
        eventRepository.save(new Event(text, userId));
    }
}
