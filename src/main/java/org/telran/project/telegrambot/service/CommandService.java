package org.telran.project.telegrambot.service;

/**
 * CommandService interface to implement to work with commands bot has received from channels/groups
 *
 * @author Olegs Gercens
 */
public interface CommandService {

    /**
     * Command processing method to make logic with command bot received from channel/group
     * with specific external channel identifier
     *
     * @param command   bot received from channel/group
     * @param channelId specific external identifier of channel/group
     */
    void executeCommand(String command, long channelId);
}
