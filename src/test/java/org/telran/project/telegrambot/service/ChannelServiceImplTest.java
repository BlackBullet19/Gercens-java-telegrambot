package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.repository.ChannelRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ChannelServiceImplTest {

    @Autowired
    private ChannelRepository channelRepository;

    private ChannelService channelService;

    @BeforeEach
    void setUp() {
        channelService= new ChannelServiceImpl(channelRepository);
        channelRepository.save(new Channel("channel", 100));
        channelRepository.save(new Channel("channelTwo", 200));
    }

    @AfterEach
    void tearDown() {
        channelRepository.deleteAll();
    }

    @Test
    void canGetChannel() {
        Channel channel = channelService.getChannel(100);
        assertEquals("channel", channel.getName());
    }

    @Test
    void canCreateChannelWithConstructor() {
        channelService.createChannel(new Channel("channelThree", 300));
        Channel channel = channelService.getChannel(300);
        assertEquals("channelThree", channel.getName());
    }

    @Test
    void canDeleteChannel() {
        channelService.deleteChannel(100);
        List<Channel> channels = channelService.listChannels();
        assertEquals(1, channels.size());
    }

    @Test
    void canUpdateChannel() {
        Channel channel = new Channel("channelThree", 300);
        channelService.createChannel(channel);
        Channel updatedChannel = channelService.updateChannel(300);
        assertEquals(channel, updatedChannel);
    }

    @Test
    void canGetListOfChannels() {
        List<Channel> channels = channelService.listChannels();
        assertEquals(2, channels.size());
    }

    @Test
    void canCreateChannelWithChannel() {
        Channel channel = new Channel("channelThree", 300);
        channelService.createChannel(channel);
        Channel gotChannel = channelService.getChannel(300);
        assertEquals(channel, gotChannel);
    }

    @Test
    void canChangeBotEnabledForChannelToTrue() {
        Channel channelBefore = new Channel("test", 150);
        channelBefore.setBotEnabled(false);
        channelService.createChannel(channelBefore);
        channelService.on(150);
        Channel channelAfter = channelService.getChannel(150);
        assertTrue(channelAfter.isBotEnabled());
    }

    @Test
    void canChangeBotEnabledForChannelToFalse() {
        Channel channelBefore = new Channel("test", 150);
        channelBefore.setBotEnabled(true);
        channelService.createChannel(channelBefore);
        channelService.off(150);
        Channel channelAfter = channelService.getChannel(150);
        assertFalse(channelAfter.isBotEnabled());
    }
}