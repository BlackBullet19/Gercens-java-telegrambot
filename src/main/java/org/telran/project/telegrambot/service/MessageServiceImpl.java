package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Message;
import org.telran.project.telegrambot.repository.MessageRepository;

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
    public Message getMessage(int messageId) {
        return messageRepository.findByMessageId(messageId);
    }

    @Override
    public void createMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void removeMessage(int messageId) {
        messageRepository.delete(getMessage(messageId));
    }

    @Override
    public Message updateMessage(int messageId) {
        return messageRepository.findByMessageId(messageId);
    }

    @Override
    public void createMessage(int messageId, String title, long chatId, String text) {
        messageRepository.save(new Message(messageId,title,chatId,text));
    }

    @Override
    public List<Message> listAllNewMessages() {
        return messageRepository.findAllNewMessages();
    }

    @Override
    public void changeIsNewToFalse(int fromId, int toId) {
        messageRepository.changeIsNewToFalse(fromId, toId);
    }

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public MessageServiceImpl() {
    }
}
