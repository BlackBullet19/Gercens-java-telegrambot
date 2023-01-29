package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.Message;

import java.util.List;

/**
 * MessageService interface to implement to create and get messages received from bot
 *
 * @author Olegs Gercens
 */
public interface MessageService {

    /**
     * Method to implement that registers to save an entity Message
     *
     * @param title  channel/group title
     * @param chatId external identifier
     * @param text   of message
     * @return entity Message
     */
    Message createMessage(String title, long chatId, String text);

    /**
     * Method to implement that returns new messages and changes it status to old
     *
     * @return a list of new messages
     */
    List<Message> getMessagesAndMarkThemOld();
}
