package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.commands.Command;

/**
 * CommandService implementation to work with commands bot has received from channels/groups
 *
 * @author Olegs Gercens
 */
@Service
public class CommandServiceImpl implements CommandService {

    @Autowired
    private ChannelService channelService;

    /**
     * Command processing method to make logic with command bot received from channel/group
     * with specific external channel identifier
     *
     * @param command   bot received from channel/group
     * @param channelId specific external identifier of channel/group
     * @throws IllegalArgumentException - if identifier is 0 or command is empty
     */
    @Override
    public void executeCommand(String command, long channelId) {
        if (channelId == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (command == null) throw new IllegalArgumentException("Entered value can not be empty");
        if (Command.START.getName().equals(command)) {
            channelService.on(channelId);
        } else if (Command.STOP.getName().equals(command)) {
            channelService.off(channelId);
        }
    }
}
