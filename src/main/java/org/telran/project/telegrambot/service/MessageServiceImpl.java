package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Message;
import org.telran.project.telegrambot.repository.MessageRepository;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * MessageService implementation to create and get messages received from bot
 *
 * @author Olegs Gercens
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    /**
     * Registers to save entity Message
     *
     * @param title  channel/group title
     * @param chatId external identifier
     * @param text   of message
     * @return entity Message
     * @throws InvalidParameterException - if at least one entered parameter was 0 or empty
     */
    @Override
    public Message createMessage(String title, long chatId, String text) {
        if (chatId == 0 || title == null) {
            throw new InvalidParameterException("At least one entered parameter was 0 or empty");
        }
        Message newMessage = new Message(title, chatId, text);
        return messageRepository.save(newMessage);
    }

    /**
     * Method that gets new messages, then change status new to old(isNew=true to isNew=false),
     * then gives this list further to create events
     *
     * @return a list of new messages
     */
    @Override
    public List<Message> getMessagesAndMarkThemOld() {
        List<Message> messages = messageRepository.findAllNewMessages();
        if (!messages.isEmpty()) {
            List<Integer> idsList = messages.stream().map(Message::getId).toList();
            messageRepository.changeIsNewToFalse(idsList);
        }
        return messages;
    }
}
