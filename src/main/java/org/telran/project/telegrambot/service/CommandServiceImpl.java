package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.commands.Command;

@Service
public class CommandServiceImpl implements CommandService {

    @Autowired
    private ChannelService channelService;

    @Override
    public void executeCommand(String command,long channelId) {

        if (Command.START.getName().equals(command)) {
            channelService.on(channelId);
        } else if (Command.STOP.getName().equals(command)) {
            channelService.off(channelId);
        }
    }
}
