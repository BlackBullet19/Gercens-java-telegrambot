package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandServiceImplTest {

    @Mock
    private ChannelService channelService;

    private CommandService commandService;

    @BeforeEach
    void setUp() {
        commandService = new CommandServiceImpl(channelService);
    }

    @Test
    void canExecuteCommand() {
        commandService.executeCommand("/start", 1);
        verify(channelService).on(1);
    }
}