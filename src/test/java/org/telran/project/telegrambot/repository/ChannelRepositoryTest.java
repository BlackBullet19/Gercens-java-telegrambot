package org.telran.project.telegrambot.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.service.ChannelService;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ChannelRepositoryTest {

    @Autowired
    private ChannelRepository channelRepository;

    private Channel channel;

    @BeforeEach
    void setUp() {
        channel = new Channel("channel", 100);
        channelRepository.save(channel);
    }

    @AfterEach
    void tearDown() {
        channelRepository.deleteAll();
    }

    @Test
    void canFindByChannelId() {
        Channel byChannelId = channelRepository.findByChannelId(100);
        assertEquals("channel", byChannelId.getName());
    }

    @Test
    void ifExistsByChannelIdThenTrue() {
        assertTrue(channelRepository.existsByChannelId(100));
    }

    @Test
    void canChangeIsBotEnabledToFalse() {

        channelRepository.changeIsBotEnabledToFalse(channel.getChannelId());
        Channel byChannelId = channelRepository.findByChannelId(channel.getChannelId());
        assertEquals(false, byChannelId.isBotEnabled());

    }

    @Test
    void canChangeIsBotEnabledToTrue() {
        channelRepository.changeIsBotEnabledToFalse(100);
        channelRepository.changeIsBotEnabledToTrue(100);
        Channel byChannelId = channelRepository.findByChannelId(channel.getChannelId());
        assertEquals(true, byChannelId.isBotEnabled());
    }
}