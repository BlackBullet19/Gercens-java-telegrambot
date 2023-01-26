package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Message;
import org.telran.project.telegrambot.repository.MessageRepository;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public List<Message> list() {
        return messageRepository.findAll();
    }

    @Override
    public Message getMessage(int id) {
        if (messageRepository.existsById(id)) {
            return messageRepository.findById(id).get();
        }
        throw new IllegalArgumentException("Invalid channel identifier");
    }

    @Override
    public Message createMessage(Message message) {
        if (message.getChatId() == 0 || message.getTitle() == null) {
            throw new IllegalArgumentException("Parameters are not valid");
        }
        Message newMessage = new Message(message.getTitle(), message.getChatId(), message.getText());
        return messageRepository.save(newMessage);
    }

    @Override
    public void removeMessage(int id) {
        if (messageRepository.existsById(id)) {
            Message message = messageRepository.findById(id).get();
            messageRepository.delete(message);
        }
        throw new IllegalArgumentException("Invalid identifier");
    }

    @Override
    public Message updateMessage(int id, Message message) {
        if (messageRepository.existsById(id)) {
            Message existingMessage = messageRepository.findById(id).get();
            if (message != null) {
                existingMessage.setChatId(message.getChatId());
                existingMessage.setTitle(message.getTitle());
            }
            return messageRepository.save(existingMessage);
        }
        throw new InvalidParameterException("Parameters are not valid");
    }

    @Override
    public Message createMessage(String title, long chatId, String text) {
        if (chatId == 0 || title == null) {
            throw new IllegalArgumentException("Parameters are not valid");
        }
        Message newMessage = new Message(title, chatId, text);
        return messageRepository.save(newMessage);
    }

    @Override
    public List<Message> listAllNewMessages() {
        return messageRepository.findAllNewMessages();
    }

    @Override
    public void changeIsNewToFalse(List<Integer> Ids) {
        messageRepository.changeIsNewToFalse(Ids);
    }

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
