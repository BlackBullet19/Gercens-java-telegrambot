package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> list();

    Message getMessage(int messageId);

    Message createMessage(Message message);

    void removeMessage(int messageId);

    Message updateMessage(int messageId, Message message);

    Message createMessage(String title, long chatId, String text);

    List<Message> listAllNewMessages();

    void changeIsNewToFalse(List<Integer> Ids);

    List<Message> getMessagesAndMarkThemOld();
}
