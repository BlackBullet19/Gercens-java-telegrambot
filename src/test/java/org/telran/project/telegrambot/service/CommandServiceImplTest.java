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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class CommandServiceImplTest {

    @Autowired
    private CommandService commandService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ChannelRepository channelRepository;

    @BeforeEach
    void setUp() {
        channelRepository.deleteAll();
    }

    @Test
    void executeCommandStartWithRightCommandAndExistingChannel() {
        String command = "/start";
        Channel channel = new Channel("channel", 456);
        channelRepository.save(channel);
        channelRepository.changeIsBotEnabledToFalse(456);
        Channel channelFromRepositoryBotFalse = channelRepository.findByChannelId(456);
        assertFalse(channelFromRepositoryBotFalse.isBotEnabled());
        commandService.executeCommand(command, 456);
        Channel channelFromRepositoryBotTrue = channelRepository.findByChannelId(456);
        assertTrue(channelFromRepositoryBotTrue.isBotEnabled());
    }

    @Test
    void executeCommandStartWithRightCommandAndNullChannelId() {
        String command = "/start";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> commandService.executeCommand(command, 0));
        assertEquals("Entered value can not be 0", exception.getMessage());
    }

    @Test
    void executeCommandStopWithRightCommandAndExistingChannel() {
        String command = "/stop";
        Channel channel = new Channel("channel", 456);
        channelRepository.save(channel);
        Channel channelFromRepositoryBotTrue = channelRepository.findByChannelId(456);
        assertTrue(channelFromRepositoryBotTrue.isBotEnabled());
        commandService.executeCommand(command, 456);
        Channel channelFromRepositoryBotFalse = channelRepository.findByChannelId(456);
        assertFalse(channelFromRepositoryBotFalse.isBotEnabled());
    }
}