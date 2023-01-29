package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.model.User;
import org.telran.project.telegrambot.model.UserChannel;
import org.telran.project.telegrambot.repository.ChannelRepository;
import org.telran.project.telegrambot.repository.UserChannelRepository;
import org.telran.project.telegrambot.repository.UserRepository;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UserChannelServiceImplTest {

    @Autowired
    private UserChannelService userChannelService;

    @Autowired
    private UserChannelRepository userChannelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ChannelRepository channelRepository;

    @BeforeEach
    void setUp() {
        userChannelRepository.deleteAll();
        userRepository.deleteAll();
        channelRepository.deleteAll();
    }


    @Test
    void listUserChannelsWithRightUserId() {
        List<UserChannel> emptyUserChannelList = userChannelRepository.findAll();
        assertEquals(0, emptyUserChannelList.size());
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        Channel channelTwo = channelRepository.save(new Channel("channelTwo", 124));
        Channel channelThree = channelRepository.save(new Channel("channelThree", 125));
        User user = userRepository.save(new User("user"));
        userChannelRepository.save(new UserChannel(user.getId(), channelOne.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelTwo.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelThree.getId()));
        List<UserChannel> userChannels = userChannelService.listUserChannels(user.getId());
        assertEquals(3, userChannels.size());
    }

    @Test
    void listUserChannelsWithNotExistingUserId() {
        List<UserChannel> emptyUserChannelList = userChannelRepository.findAll();
        assertEquals(0, emptyUserChannelList.size());
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        Channel channelTwo = channelRepository.save(new Channel("channelTwo", 124));
        Channel channelThree = channelRepository.save(new Channel("channelThree", 125));
        User user = userRepository.save(new User("user"));
        userChannelRepository.save(new UserChannel(user.getId(), channelOne.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelTwo.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelThree.getId()));
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userChannelService.listUserChannels(555));
        assertEquals("User with ID 555 not found", exception.getMessage());
    }

    @Test
    void listUserChannelsWithNullUserId() {
        List<UserChannel> emptyUserChannelList = userChannelRepository.findAll();
        assertEquals(0, emptyUserChannelList.size());
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        Channel channelTwo = channelRepository.save(new Channel("channelTwo", 124));
        Channel channelThree = channelRepository.save(new Channel("channelThree", 125));
        User user = userRepository.save(new User("user"));
        userChannelRepository.save(new UserChannel(user.getId(), channelOne.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelTwo.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelThree.getId()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userChannelService.listUserChannels(0));
        assertEquals("Entered value can not be 0", exception.getMessage());
    }

    @Test
    void removeUserSubscriptionWithExistingUserIdAndExistingChannelId() {
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        Channel channelTwo = channelRepository.save(new Channel("channelTwo", 124));
        Channel channelThree = channelRepository.save(new Channel("channelThree", 125));
        User user = userRepository.save(new User("user"));
        userChannelRepository.save(new UserChannel(user.getId(), channelOne.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelTwo.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelThree.getId()));
        List<UserChannel> userChannels = userChannelRepository.findByUserId(user.getId());
        assertEquals(3, userChannels.size());
        userChannelService.removeUserSubscription(user.getId(), channelOne.getId());
        List<UserChannel> userChannelsAfterDelete = userChannelRepository.findByUserId(user.getId());
        assertEquals(2, userChannelsAfterDelete.size());
        assertFalse(userChannelRepository.existsByUserIdAndChannelId(user.getId(), channelOne.getId()));
    }

    @Test
    void removeUserSubscriptionWithExistingUserIdAndNotExistingChannelId() {
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        Channel channelTwo = channelRepository.save(new Channel("channelTwo", 124));
        Channel channelThree = channelRepository.save(new Channel("channelThree", 125));
        User user = userRepository.save(new User("user"));
        userChannelRepository.save(new UserChannel(user.getId(), channelOne.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelTwo.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelThree.getId()));
        List<UserChannel> userChannels = userChannelRepository.findByUserId(user.getId());
        assertEquals(3, userChannels.size());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userChannelService.removeUserSubscription(user.getId(), 54));
        assertEquals("Subscription with ChannelId 54 and UserId " + user.getId() + " not found", exception.getMessage());
        List<UserChannel> userChannelsAfterDelete = userChannelRepository.findByUserId(user.getId());
        assertEquals(3, userChannelsAfterDelete.size());
    }

    @Test
    void removeUserSubscriptionWithOneNullField() {
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        Channel channelTwo = channelRepository.save(new Channel("channelTwo", 124));
        Channel channelThree = channelRepository.save(new Channel("channelThree", 125));
        User user = userRepository.save(new User("user"));
        userChannelRepository.save(new UserChannel(user.getId(), channelOne.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelTwo.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelThree.getId()));
        List<UserChannel> userChannels = userChannelRepository.findByUserId(user.getId());
        assertEquals(3, userChannels.size());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userChannelService.removeUserSubscription(0, channelOne.getId()));
        assertEquals("At least one entered parameter was 0", exception.getMessage());
        List<UserChannel> userChannelsAfterDelete = userChannelRepository.findByUserId(user.getId());
        assertEquals(3, userChannelsAfterDelete.size());
    }

    @Test
    void addUserSubscriptionWithRightUserIdAndAllNonNullFields() {
        List<UserChannel> emptyUserChannelList = userChannelRepository.findAll();
        assertEquals(0, emptyUserChannelList.size());
        Channel channel = channelRepository.save(new Channel("channelOne", 123));
        User user = userRepository.save(new User("user"));
        UserChannel newSubscription = new UserChannel(user.getId(), channel.getId());
        UserChannel addedUserSubscription = userChannelService.addUserSubscription(user.getId(), newSubscription);
        List<UserChannel> userChannelList = userChannelRepository.findByUserId(user.getId());
        assertEquals(1, userChannelList.size());
        UserChannel userChannel = userChannelList.get(0);
        assertEquals(channel.getId(), userChannel.getChannelId());
    }

    @Test
    void addUserSubscriptionWithRightUserIdAndOneNullField() {
        List<UserChannel> emptyUserChannelList = userChannelRepository.findAll();
        assertEquals(0, emptyUserChannelList.size());
        Channel channel = channelRepository.save(new Channel("channelOne", 123));
        User user = userRepository.save(new User("user"));
        UserChannel newSubscription = new UserChannel(0, channel.getId());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userChannelService.addUserSubscription(user.getId(), newSubscription));
        assertEquals("At least one entered parameter was 0", exception.getMessage());
        List<UserChannel> userChannelList = userChannelRepository.findByUserId(user.getId());
        assertEquals(0, userChannelList.size());
    }

    @Test
    void addUserOneMoreExistingSubscriptionWithExistingUserIdAndAllNonNullField() {
        List<UserChannel> emptyUserChannelList = userChannelRepository.findAll();
        assertEquals(0, emptyUserChannelList.size());
        Channel channel = channelRepository.save(new Channel("channelOne", 123));
        User user = userRepository.save(new User("user"));
        UserChannel newSubscription = new UserChannel(user.getId(), channel.getId());
        userChannelService.addUserSubscription(user.getId(), newSubscription);
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> userChannelService.addUserSubscription(user.getId(), newSubscription));
        assertEquals("Subscription with entered parameters already exists", exception.getMessage());
        List<UserChannel> userChannelList = userChannelRepository.findByUserId(user.getId());
        assertEquals(1, userChannelList.size());
    }

    @Test
    void addUserSubscriptionWithExistingUserIdAndOneNullField() {
        List<UserChannel> emptyUserChannelList = userChannelRepository.findAll();
        assertEquals(0, emptyUserChannelList.size());
        Channel channel = channelRepository.save(new Channel("channelOne", 123));
        User user = userRepository.save(new User("user"));
        UserChannel newSubscription = new UserChannel(user.getId(), 0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userChannelService.addUserSubscription(user.getId(), newSubscription));
        assertEquals("At least one entered parameter was 0", exception.getMessage());
        List<UserChannel> userChannelList = userChannelRepository.findByUserId(user.getId());
        assertEquals(0, userChannelList.size());
    }

    @Test
    void findAllUserChannelsByChannelIdFromIdsList() {
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        Channel channelTwo = channelRepository.save(new Channel("channelTwo", 124));
        Channel channelThree = channelRepository.save(new Channel("channelThree", 125));
        User user = userRepository.save(new User("user"));
        userChannelRepository.save(new UserChannel(user.getId(), channelOne.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelTwo.getId()));
        userChannelRepository.save(new UserChannel(user.getId(), channelThree.getId()));
        List<Integer> channelIdsList = new ArrayList<>();
        channelIdsList.add(channelOne.getId());
        channelIdsList.add(channelTwo.getId());
        List<UserChannel> channelRepositoryAll = userChannelRepository.findAll();
        assertEquals(3, channelRepositoryAll.size());
        List<UserChannel> allUserChannelsByChannelId = userChannelService
                .findAllUserChannelsByChannelIdFromIdsList(channelIdsList);
        assertEquals(2, allUserChannelsByChannelId.size());
    }

    @Test
    void activateSubscriptionWithAllNonNullFields() {
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        User user = userRepository.save(new User("user"));
        UserChannel userChannel = new UserChannel(user.getId(), channelOne.getId());
        userChannel.setSubscribed(false);
        UserChannel savedUserChannel = userChannelRepository.save(userChannel);
        assertFalse(savedUserChannel.isSubscribed());
        userChannelService.activateSubscription(user.getId(), savedUserChannel);
        UserChannel byUserIdAndChannelId = userChannelRepository
                .findByUserIdAndChannelId(user.getId(), channelOne.getId());
        assertTrue(byUserIdAndChannelId.isSubscribed());
    }

    @Test
    void activateSubscriptionWithFalseUserIdAndAllNonNullFields() {
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        User user = userRepository.save(new User("user"));
        UserChannel userChannel = new UserChannel(user.getId(), channelOne.getId());
        userChannel.setSubscribed(false);
        UserChannel savedUserChannel = userChannelRepository.save(userChannel);
        assertFalse(savedUserChannel.isSubscribed());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userChannelService.activateSubscription(55, savedUserChannel));
        assertEquals("User with ID 55 not found", exception.getMessage());
        UserChannel byUserIdAndChannelId = userChannelRepository
                .findByUserIdAndChannelId(user.getId(), channelOne.getId());
        assertFalse(byUserIdAndChannelId.isSubscribed());
    }

    @Test
    void activateSubscriptionWithRightUserIdAndOneNullField() {
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        User user = userRepository.save(new User("user"));
        UserChannel userChannel = new UserChannel(user.getId(), channelOne.getId());
        userChannel.setSubscribed(false);
        UserChannel savedUserChannel = userChannelRepository.save(userChannel);
        assertFalse(savedUserChannel.isSubscribed());
        InvalidParameterException exception = assertThrows(InvalidParameterException.class,
                () -> userChannelService.activateSubscription(user.getId(), new UserChannel(user.getId(), 0)));
        assertEquals("At least one entered parameter was 0", exception.getMessage());
        UserChannel byUserIdAndChannelId = userChannelRepository
                .findByUserIdAndChannelId(user.getId(), channelOne.getId());
        assertFalse(byUserIdAndChannelId.isSubscribed());
    }

    @Test
    void activateSubscriptionWhichDoesntExist() {
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        User user = userRepository.save(new User("user"));
        UserChannel userChannel = new UserChannel(user.getId(), channelOne.getId());
        userChannel.setSubscribed(false);
        UserChannel savedUserChannel = userChannelRepository.save(userChannel);
        assertFalse(savedUserChannel.isSubscribed());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userChannelService.activateSubscription(user.getId(), new UserChannel(user.getId(), 555)));
        assertEquals("Subscription with entered parameters doesn't exist", exception.getMessage());
        UserChannel byUserIdAndChannelId = userChannelRepository
                .findByUserIdAndChannelId(user.getId(), channelOne.getId());
        assertFalse(byUserIdAndChannelId.isSubscribed());
    }

    @Test
    void deactivateSubscriptionWithAllNonNullFields() {
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        User user = userRepository.save(new User("user"));
        UserChannel userChannel = new UserChannel(user.getId(), channelOne.getId());
        UserChannel savedUserChannel = userChannelRepository.save(userChannel);
        assertTrue(savedUserChannel.isSubscribed());
        userChannelService.deactivateSubscription(user.getId(), savedUserChannel);
        UserChannel byUserIdAndChannelId = userChannelRepository
                .findByUserIdAndChannelId(user.getId(), channelOne.getId());
        assertFalse(byUserIdAndChannelId.isSubscribed());
    }

    @Test
    void deactivateSubscriptionWithFalseUserIdAndAllNonNullFields() {
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        User user = userRepository.save(new User("user"));
        UserChannel userChannel = new UserChannel(user.getId(), channelOne.getId());
        UserChannel savedUserChannel = userChannelRepository.save(userChannel);
        assertTrue(savedUserChannel.isSubscribed());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userChannelService.deactivateSubscription(55, savedUserChannel));
        assertEquals("User with ID 55 not found", exception.getMessage());
        UserChannel byUserIdAndChannelId = userChannelRepository
                .findByUserIdAndChannelId(user.getId(), channelOne.getId());
        assertTrue(byUserIdAndChannelId.isSubscribed());
    }

    @Test
    void deactivateSubscriptionWithRightUserIdAndOneNullField() {
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        User user = userRepository.save(new User("user"));
        UserChannel userChannel = new UserChannel(user.getId(), channelOne.getId());
        UserChannel savedUserChannel = userChannelRepository.save(userChannel);
        assertTrue(savedUserChannel.isSubscribed());
        InvalidParameterException exception = assertThrows(InvalidParameterException.class,
                () -> userChannelService.deactivateSubscription(user.getId(), new UserChannel(user.getId(), 0)));
        assertEquals("At least one entered parameter was 0", exception.getMessage());
        UserChannel byUserIdAndChannelId = userChannelRepository
                .findByUserIdAndChannelId(user.getId(), channelOne.getId());
        assertTrue(byUserIdAndChannelId.isSubscribed());
    }

    @Test
    void deactivateSubscriptionWhichDoesntExist() {
        Channel channelOne = channelRepository.save(new Channel("channelOne", 123));
        User user = userRepository.save(new User("user"));
        UserChannel userChannel = new UserChannel(user.getId(), channelOne.getId());
        UserChannel savedUserChannel = userChannelRepository.save(userChannel);
        assertTrue(savedUserChannel.isSubscribed());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userChannelService.deactivateSubscription(user.getId(), new UserChannel(user.getId(), 555)));
        assertEquals("Subscription with entered parameters doesn't exist", exception.getMessage());
        UserChannel byUserIdAndChannelId = userChannelRepository
                .findByUserIdAndChannelId(user.getId(), channelOne.getId());
        assertTrue(byUserIdAndChannelId.isSubscribed());
    }
}