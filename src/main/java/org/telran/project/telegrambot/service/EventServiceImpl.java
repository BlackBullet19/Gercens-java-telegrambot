package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Event;
import org.telran.project.telegrambot.model.Message;
import org.telran.project.telegrambot.model.UserChannel;
import org.telran.project.telegrambot.repository.EventRepository;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {


    @Autowired
    private MessageService messageService;

    @Autowired
    private UserChannelService userChannelService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private EventRepository eventRepository;


    @Scheduled(fixedRateString = "${method.schedule}")
    @Override
    public void createNewEvents() {

        List<Message> messages = messageService.getMessagesAndMarkThemOld();
        if (messages.isEmpty()) return;

        List<Long> uniqueChannelIdsFromMessages = messages.stream().map(Message::getChatId).distinct().toList();

        List<Integer> channelIds = channelService.
                findAllIdsByChannelIdFromUniqueChannelIdsList(uniqueChannelIdsFromMessages);

        List<UserChannel> allUserChannelsByChannelIdFromIdsList = userChannelService
                .findAllUserChannelsByChannelIdFromIdsList(channelIds);

        String text = "Hi! You have new messages";

        allUserChannelsByChannelIdFromIdsList.forEach(userChannel ->
                createEvent(userChannel.getUserId(), text, userChannel.getChannelId()));
    }

    @Override
    public List<Event> getNewEventsByUserId(int userId) {
        if (eventRepository.existsByUserId(userId)) {
            List<Event> allNewEventsByUserId = eventRepository.findAllByUserId(userId);
            allNewEventsByUserId.forEach(ev -> ev.setNew(false));
            return eventRepository.saveAll(allNewEventsByUserId);
        }
        throw new IllegalArgumentException("userId is not valid");
    }

    @Override
    public void createEvent(int userId, String text, int channelId) {
        if (userId == 0) {
            throw new IllegalArgumentException("userId is not valid");
        }
        if (channelId == 0) {
            throw new IllegalArgumentException("channelId is not valid");
        }
        eventRepository.save(new Event(text, userId, channelId));
    }
}
