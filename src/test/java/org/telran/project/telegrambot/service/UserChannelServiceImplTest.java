package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.model.UserChannel;
import org.telran.project.telegrambot.repository.UserChannelRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserChannelServiceImplTest {

    private UserChannelService userChannelService;

    @Autowired
    private UserChannelRepository userChannelRepository;

    @BeforeEach
    void setUp() {
        userChannelService = new UserChannelServiceImpl(userChannelRepository);
        userChannelRepository.save(new UserChannel(1, 11));
        userChannelRepository.save(new UserChannel(1, 12));
        userChannelRepository.save(new UserChannel(2, 11));

    }

    @AfterEach
    void tearDown() {
        userChannelRepository.deleteAll();
    }

    @Test
    void canGetListOfUserChannels() {
        List<UserChannel> userChannels = userChannelService.listUserChannels(1);
        assertEquals(2, userChannels.size());
    }

    @Test
    void canAddUserSubscription() {
        userChannelService.addUserSubscription(2, 12);
        List<UserChannel> userChannels = userChannelService.listUserChannels(2);
        assertEquals(2, userChannels.size());
    }

    @Test
    void canRemoveUserSubscriptionWithChannelId() {
        userChannelService.removeUserSubscription(1,11);
        List<UserChannel> userChannels = userChannelService.listUserChannels(1);
        assertEquals(1, userChannels.size());

    }

    @Test
    void canAddUserSubscriptionWithObjectChannel() {
        Channel channel = new Channel("title", 12);
        userChannelService.addUserSubscription(2, channel);
        List<UserChannel> userChannels = userChannelService.listUserChannels(2);
        assertEquals(2, userChannels.size());
    }

    @Test
    void canGetListAllUserChannels() {
        List<UserChannel> listAll = userChannelService.listAll();
        assertEquals(3, listAll.size());
    }

    @Test
    void canCreateUserChannelWithoutSavingToRepository() {
        UserChannel userChannel = userChannelService
                .createUserChannelWithoutSavingToRepository(1, 15);
        assertEquals(15, userChannel.getChannelId());
    }

    @Test
    void canSaveAllUsersFromList() {
        List<UserChannel> list = new ArrayList<>();
        list.add(new UserChannel(5, 19));
        list.add(new UserChannel(5, 19));
        list.add(new UserChannel(4, 19));
        list.add(new UserChannel(4, 19));
        list.add(new UserChannel(2, 19));
        userChannelService.saveAllUsers(list);
        List<UserChannel> userChannels = userChannelService.listAll();
        assertEquals(8, userChannels.size());
    }
}