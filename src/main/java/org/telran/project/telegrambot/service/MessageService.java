package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> list();

    Message getMessage(int messageId);

    void createMessage(Message message);

    void removeMessage(int messageId);

    Message updateMessage(int messageId);

    void createMessage(int messageId, String title, long chatId, String text);

    List<Message> listAllNewMessages();

    void changeIsNewToFalse(int id);
}
