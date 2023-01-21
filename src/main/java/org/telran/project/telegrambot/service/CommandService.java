package org.telran.project.telegrambot.service;


public interface CommandService {

    void executeCommand(String command,long channelId);
}
