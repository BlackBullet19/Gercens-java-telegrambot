package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.TelegramMessage;
import org.telran.project.telegrambot.repository.TelegramMessageRepository;

import java.util.List;

@Service
public class TelegramMessageServiceImpl implements TelegramMessageService {

    @Autowired
    TelegramMessageRepository messageRepository;

    @Override
    public List<TelegramMessage> list() {
        return messageRepository.findAll();
    }

    @Override
    public TelegramMessage getMessage(int messageId) {
        return messageRepository.findByMessageId(messageId);
    }

    @Override
    public void createMessage(TelegramMessage message) {
        messageRepository.save(message);
    }

    @Override
    public void removeMessage(int messageId) {
        messageRepository.delete(getMessage(messageId));
    }

    @Override
    public TelegramMessage updateMessage(int messageId) {
        return getMessage(messageId);
    }
}
