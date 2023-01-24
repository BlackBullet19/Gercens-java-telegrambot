package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.*;
import org.telran.project.telegrambot.repository.EventRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@PropertySource("schedule.properties")
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


    @Scheduled(fixedRateString = "${method.schedule}")
    @Override
    public void createNewEvents() {

        List<Message> messages = getMessagesAndMarkThemOld();
        if (messages.isEmpty()) return;

        List<User> userList = userService.list();
        List<Channel> channelList = channelService.listChannels();
        List<Long> listOfChannelIds = getListOfUniqueChannelIds(messages);

        listOfChannelIds.forEach(channelId -> {
            List<Integer> allSubscribedUserIdsByChannelId = getAllSubscribedUsersForThisChannels(channelId);
            allSubscribedUserIdsByChannelId.forEach(u -> {
                String username = getUsername(userList, u);
                String channelName = getChannelName(channelList, channelId);

                String text = "Hello, " + username + " in group - '"
                        + channelName + "' you have new messages!";
                createEvent(u, text);
            });
        });
    }

    private String getChannelName(List<Channel> channelList, Long channelId) {
        String channelName = null;
        Channel channel = channelList.stream()
                .filter(ch -> ch.getChannelId() == channelId).findFirst().orElse(null);
        if (channel != null) {
            channelName = channel.getName();
        }
        return channelName;
    }

    private String getUsername(List<User> userList, Integer u) {
        String username = null;
        User user = userList.stream()
                .filter(el -> el.getId() == u).findFirst().orElse(null);
        if (user != null) {
            username = user.getName();
        }
        return username;
    }

    private List<Integer> getAllSubscribedUsersForThisChannels(long channelId) {
        List<UserChannel> allUserChannels = userChannelService.listAll();
        List<Integer> allSubscribedUserIdsByChannelId = new ArrayList<>();
        allUserChannels.forEach(channel -> {
            if (channel.getChannelId() == channelId) {
                allSubscribedUserIdsByChannelId.add(channel.getUserId());
            }
        });
        return allSubscribedUserIdsByChannelId;
    }

    private List<Long> getListOfUniqueChannelIds(List<Message> messages) {
        Set<Long> channelIds = new HashSet<>();
        for (Message m : messages) {
            channelIds.add(m.getChatId());
        }
        return channelIds.stream().toList();
    }

    private List<Message> getMessagesAndMarkThemOld() {
        List<Message> messages = messageService.listAllNewMessages();
        if (!messages.isEmpty()) {
            Message firstNewMessage = messages.get(0);
            int fromId = firstNewMessage.getId();
            Message lastNewMessage = messages.get(messages.size() - 1);
            int toId = lastNewMessage.getId();
            messageService.changeIsNewToFalse(fromId, toId);
        }
        return messages;
    }

    @Override
    public List<Event> getNewEventsByUserId(int userId) {
        return eventRepository.findAllByUserId(userId);
    }

    @Override
    public void createEvent(int userId, String text) {
        eventRepository.save(new Event(text, userId));
    }

    public EventServiceImpl() {
    }

    public EventServiceImpl(MessageService messageService, UserChannelService userChannelService,
                            UserService userService, ChannelService channelService, EventRepository eventRepository) {
        this.messageService = messageService;
        this.userChannelService = userChannelService;
        this.userService = userService;
        this.channelService = channelService;
        this.eventRepository = eventRepository;
    }
}
