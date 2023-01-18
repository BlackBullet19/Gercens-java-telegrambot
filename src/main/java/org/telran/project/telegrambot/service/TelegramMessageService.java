package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.TelegramMessage;

import java.util.List;

public interface TelegramMessageService {

    List<TelegramMessage> list();

    TelegramMessage getMessage(int messageId);

    void createMessage(TelegramMessage message);

    void removeMessage(int messageId);

    TelegramMessage updateMessage(int messageId);
}
