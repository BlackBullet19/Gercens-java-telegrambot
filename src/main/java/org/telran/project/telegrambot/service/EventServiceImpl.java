package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Event;
import org.telran.project.telegrambot.model.Message;
import org.telran.project.telegrambot.model.UserChannel;
import org.telran.project.telegrambot.repository.EventRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * EventService implementation to create and display events for users, based on all stored data
 *
 * @author Olegs Gercens
 */
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

    @Autowired
    private UserService userService;

    /**
     * Main method to generate event for specific user in 5 steps.
     * <p>
     * Step 1 - collect all new messages received by bot
     * Step 2 - prepare list with only unique values of external channel/group identifiers
     * messages came from
     * Step 3 - get list with internal channel/group identifiers of Channels,
     * based on match of external identifiers
     * Step 4 - get all subscriptions by list of internal channel/group identifiers
     * Step 5 - for each of these subscriptions generate new event if subscription is marked as active
     */
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

        String text = "Hi! You have new message";

        allUserChannelsByChannelIdFromIdsList.forEach(userChannel -> {
            if (userChannel.isSubscribed()) {
                createEvent(userChannel.getUserId(), text, userChannel.getChannelId());
            }
        });
    }

    /**
     * Returns a list of new events for specific user
     *
     * @param userId specific user identifier
     * @return a list of new events for specific user
     * @throws IllegalArgumentException - if user identifier was 0
     * @throws NoSuchElementException   - if user not found
     */
    @Override
    public List<Event> getNewEventsByUserId(int userId) {
        if (userId == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (userService.existsById(userId)) {
            List<Event> allNewEventsByUserId = eventRepository.findAllByUserId(userId);
            allNewEventsByUserId.forEach(ev -> ev.setNew(false));
            return eventRepository.saveAll(allNewEventsByUserId);
        }
        throw new NoSuchElementException("User with ID " + userId + " not found");
    }

    /**
     * Returns a list of all events stored
     *
     * @return list of all events stored
     */
    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    /**
     * Registers to save entity Event
     *
     * @param userId    user identifier
     * @param text      of notification
     * @param channelId channel/group identifier
     * @throws IllegalArgumentException - if at least one given ID was 0
     */
    private void createEvent(int userId, String text, int channelId) {
        if (userId == 0 || channelId == 0) {
            throw new IllegalArgumentException("At least one entered parameter was 0");
        }
        eventRepository.save(new Event(text, userId, channelId));
    }
}
