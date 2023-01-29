package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.repository.ChannelRepository;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ChannelServiceImplTest {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ChannelRepository channelRepository;

    @BeforeEach
    void setUp() {
        channelRepository.deleteAll();
    }

    @Test
    void getExistingChannelById() {
        Channel channel = new Channel("channel", 123);
        Channel savedChannel = channelRepository.save(channel);
        Channel channelFromRepository = channelService.getChannel(savedChannel.getId());
        assertEquals("channel", channelFromRepository.getName());
        assertEquals(123, channelFromRepository.getChannelId());
    }

    @Test
    void getChannelWithWrongId() {
        Channel channel = new Channel("channel", 123);
        channelRepository.save(channel);
        NoSuchElementException assertThrows = assertThrows(NoSuchElementException.class,
                () -> channelService.getChannel(256));
        assertEquals("Channel with value 256 not found", assertThrows.getMessage());
    }

    @Test
    void getExistingChannelByChannelId() {
        Channel channel = new Channel("channel", 123);
        channelRepository.save(channel);
        Channel channelFromRepository = channelService.getChannelByChannelId(123);
        assertEquals("channel", channelFromRepository.getName());
    }

    @Test
    void getChannelWithWrongChannelId() {
        Channel channel = new Channel("channel", 123);
        channelRepository.save(channel);
        NoSuchElementException assertThrows = assertThrows(NoSuchElementException.class,
                () -> channelService.getChannelByChannelId(12));
        assertEquals("Channel with ID 12 not found", assertThrows.getMessage());
    }

    @Test
    void createChannelWithAllNonEmptyFields() {
        List<Channel> allChannels = channelRepository.findAll();
        assertEquals(0, allChannels.size());
        Channel channel = new Channel("channel", 123);
        channelService.createChannel(channel);
        List<Channel> allChannelsAfterSavingOneChannel = channelRepository.findAll();
        assertEquals(1, allChannelsAfterSavingOneChannel.size());
        Channel channelFromRepository = channelService.getChannelByChannelId(123);
        assertEquals("channel", channelFromRepository.getName());
    }

    @Test
    void createChannelWithEmptyFieldChannelName() {
        List<Channel> allChannels = channelRepository.findAll();
        assertEquals(0, allChannels.size());
        Channel channel = new Channel(null, 123);
        IllegalArgumentException assertThrows = assertThrows(IllegalArgumentException.class,
                () -> channelService.createChannel(channel));
        assertEquals("At least one entered parameter was 0 or empty", assertThrows.getMessage());
        List<Channel> allChannelsAfterOneSave = channelRepository.findAll();
        assertEquals(0, allChannelsAfterOneSave.size());
    }

    @Test
    void createChannelWithEmptyFieldChannelId() {
        List<Channel> allChannels = channelRepository.findAll();
        assertEquals(0, allChannels.size());
        Channel channel = new Channel("null", 0);
        IllegalArgumentException assertThrows = assertThrows(IllegalArgumentException.class,
                () -> channelService.createChannel(channel));
        assertEquals("At least one entered parameter was 0 or empty", assertThrows.getMessage());
        List<Channel> allChannelsAfterOneSave = channelRepository.findAll();
        assertEquals(0, allChannelsAfterOneSave.size());
    }

    @Test
    void createChannelWithSameFieldAsOneChannelExist() {
        List<Channel> allChannels = channelRepository.findAll();
        assertEquals(0, allChannels.size());
        Channel channelOne = new Channel("channelOne", 123);
        channelRepository.save(channelOne);
        List<Channel> allChannelsAfterOneSave = channelRepository.findAll();
        assertEquals(1, allChannelsAfterOneSave.size());
        Channel channelTwo = new Channel("channelTwo", 123);
        UnsupportedOperationException assertThrows = assertThrows(UnsupportedOperationException.class,
                () -> channelService.createChannel(channelTwo));
        assertEquals("Channel with entered parameters already exists", assertThrows.getMessage());
        List<Channel> allChannelsAfterSecondSave = channelRepository.findAll();
        assertEquals(1, allChannelsAfterSecondSave.size());
    }

    @Test
    void deleteChannelWithRightId() {
        Channel channel = new Channel("channel", 123);
        channelRepository.save(channel);
        List<Channel> allChannelsAfterOneSave = channelRepository.findAll();
        assertEquals(1, allChannelsAfterOneSave.size());
        channelService.deleteChannel(channel.getId());
        List<Channel> allChannelsAfterDelete = channelRepository.findAll();
        assertEquals(0, allChannelsAfterDelete.size());
    }

    @Test
    void deleteChannelWithWrongId() {
        Channel channel = new Channel("channel", 123);
        channelRepository.save(channel);
        List<Channel> allChannelsAfterOneSave = channelRepository.findAll();
        assertEquals(1, allChannelsAfterOneSave.size());
        NoSuchElementException assertThrows = assertThrows(NoSuchElementException.class,
                () -> channelService.deleteChannel(500));
        assertEquals("Channel with ID 500 not found", assertThrows.getMessage());
        List<Channel> allChannelsAfterDelete = channelRepository.findAll();
        assertEquals(1, allChannelsAfterDelete.size());
    }

    @Test
    void updateChannelWithAllNonEmptyFields() {
        Channel channel = new Channel("channel", 123);
        channelRepository.save(channel);
        List<Channel> allChannelsAfterOneSave = channelRepository.findAll();
        assertEquals(1, allChannelsAfterOneSave.size());
        Channel update = new Channel("newChannelName", 1234);
        channelService.updateChannel(channel.getId(), update);
        List<Channel> allChannelsAfterOneUpdate = channelRepository.findAll();
        assertEquals(1, allChannelsAfterOneUpdate.size());
        Channel updatedChannel = channelRepository.findById(channel.getId()).get();
        assertEquals("newChannelName", updatedChannel.getName());
    }

    @Test
    void updateChannelWithEmptyChannelNameField() {
        Channel channel = new Channel("channel", 123);
        channelRepository.save(channel);
        List<Channel> allChannelsAfterOneSave = channelRepository.findAll();
        assertEquals(1, allChannelsAfterOneSave.size());
        Channel update = new Channel(null, 1234);
        InvalidParameterException assertThrows = assertThrows(InvalidParameterException.class,
                () -> channelService.updateChannel(channel.getId(), update));
        assertEquals("At least one entered parameter was 0 or empty", assertThrows.getMessage());
        List<Channel> allChannelsAfterOneUpdate = channelRepository.findAll();
        assertEquals(1, allChannelsAfterOneUpdate.size());
        Channel updatedChannel = channelRepository.findById(channel.getId()).get();
        assertEquals("channel", updatedChannel.getName());
    }

    @Test
    void updateChannelWithEmptyChannelIdField() {
        Channel channel = new Channel("channel", 123);
        channelRepository.save(channel);
        List<Channel> allChannelsAfterOneSave = channelRepository.findAll();
        assertEquals(1, allChannelsAfterOneSave.size());
        Channel update = new Channel("null", 0);
        InvalidParameterException assertThrows = assertThrows(InvalidParameterException.class,
                () -> channelService.updateChannel(channel.getId(), update));
        assertEquals("At least one entered parameter was 0 or empty", assertThrows.getMessage());
        List<Channel> allChannelsAfterOneUpdate = channelRepository.findAll();
        assertEquals(1, allChannelsAfterOneUpdate.size());
        Channel updatedChannel = channelRepository.findById(channel.getId()).get();
        assertEquals("channel", updatedChannel.getName());
    }

    @Test
    void updateChannelWithWrongId() {
        Channel channel = new Channel("channel", 123);
        channelRepository.save(channel);
        List<Channel> allChannelsAfterOneSave = channelRepository.findAll();
        assertEquals(1, allChannelsAfterOneSave.size());
        Channel update = new Channel("null", 888);
        NoSuchElementException assertThrows = assertThrows(NoSuchElementException.class,
                () -> channelService.updateChannel(355, update));
        assertEquals("Channel with ID 355 not found", assertThrows.getMessage());
        List<Channel> allChannelsAfterOneUpdate = channelRepository.findAll();
        assertEquals(1, allChannelsAfterOneUpdate.size());
        Channel updatedChannel = channelRepository.findById(channel.getId()).get();
        assertEquals("channel", updatedChannel.getName());
    }

    @Test
    void listChannels() {
        List<Channel> findAllFromRepository = channelRepository.findAll();
        List<Channel> listAllFromService = channelService.listChannels();
        assertEquals(findAllFromRepository.size(), listAllFromService.size());
        Channel channelOne = new Channel("channelOne", 123);
        channelRepository.save(channelOne);
        Channel channelTwo = new Channel("channelTwo", 124);
        channelRepository.save(channelTwo);
        Channel channel = new Channel("channelThree", 125);
        channelRepository.save(channel);
        List<Channel> listChannelsAfterSave = channelService.listChannels();
        assertEquals(3, listChannelsAfterSave.size());
    }

    @Test
    void changeIsBotEnabledValueToTrueWithRightChannelId() {
        Channel channel = new Channel("channelOne", 123);
        channelRepository.save(channel);
        channelRepository.changeIsBotEnabledToFalse(123);
        Channel channelAfterChangingBotFalse = channelRepository.findByChannelId(123);
        assertFalse(channelAfterChangingBotFalse.isBotEnabled());
        channelService.on(123);
        Channel channelAfterChangingBotTrue = channelRepository.findByChannelId(123);
        assertTrue(channelAfterChangingBotTrue.isBotEnabled());
    }

    @Test
    void changeIsBotEnabledValueToTrueWithWrongChannelId() {
        Channel channel = new Channel("channelOne", 123);
        channelRepository.save(channel);
        channelRepository.changeIsBotEnabledToFalse(123);
        Channel channelAfterChangingBotFalse = channelRepository.findByChannelId(123);
        assertFalse(channelAfterChangingBotFalse.isBotEnabled());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> channelService.on(900));
        assertEquals("Channel with ChannelId 900 not found", exception.getMessage());
        Channel channelAfterChangingBotTrue = channelRepository.findByChannelId(123);
        assertFalse(channelAfterChangingBotTrue.isBotEnabled());
    }

    @Test
    void changeIsBotEnabledValueToFalseWithRightChannelId() {
        Channel channel = new Channel("channelOne", 123);
        channelRepository.save(channel);
        Channel channelBotTrue = channelRepository.findByChannelId(123);
        assertTrue(channelBotTrue.isBotEnabled());
        channelService.off(123);
        Channel channelAfterChangingBotFalse = channelRepository.findByChannelId(123);
        assertFalse(channelAfterChangingBotFalse.isBotEnabled());
    }

    @Test
    void changeIsBotEnabledValueToFalseWithWrongChannelId() {
        Channel channel = new Channel("channelOne", 123);
        channelRepository.save(channel);
        Channel channelAfterCreating = channelRepository.findByChannelId(123);
        assertTrue(channelAfterCreating.isBotEnabled());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> channelService.off(900));
        assertEquals("Channel with ChannelId 900 not found", exception.getMessage());
        Channel channelAfterChangingBotOff = channelRepository.findByChannelId(123);
        assertTrue(channelAfterChangingBotOff.isBotEnabled());
    }

    @Test
    void existsWithRightId() {
        Channel channelOne = new Channel("channelOne", 123);
        channelRepository.save(channelOne);
        List<Channel> oneChannelList = channelRepository.findAll();
        assertEquals(1, oneChannelList.size());
        assertTrue(channelService.existsById(channelOne.getId()));
    }

    @Test
    void notExistWithWrongId() {
        Channel channelOne = new Channel("channelOne", 123);
        channelRepository.save(channelOne);
        List<Channel> oneChannelList = channelRepository.findAll();
        assertEquals(1, oneChannelList.size());
        assertFalse(channelService.existsById(555));
    }

    @Test
    void findAllIdsByChannelIdFromUniqueChannelIdsList() {
        List<Long> channelIds = new ArrayList<>();
        channelIds.add(123L);
        channelIds.add(28L);
        Channel channelOne = new Channel("channelOne", 123);
        Channel channelTwo = new Channel("channelTwo", 31);
        Channel channelThree = new Channel("channelThree", 444);
        Channel channelFour = new Channel("channelFour", 28);
        channelRepository.save(channelOne);
        channelRepository.save(channelTwo);
        channelRepository.save(channelThree);
        channelRepository.save(channelFour);
        List<Integer> idsList = channelService.findAllIdsByChannelIdFromUniqueChannelIdsList(channelIds);
        assertEquals(2, idsList.size());
        assertTrue(idsList.contains(channelOne.getId()));
        assertTrue(idsList.contains(channelFour.getId()));
    }
}