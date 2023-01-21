package org.telran.project.telegrambot.service;

public interface Switchable {

    void on(long channelId);

    void off(long channelId);
}
