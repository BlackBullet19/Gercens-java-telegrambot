package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.model.TelegramMessage;

import java.util.List;


@Service
public class MyTelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private TelegramMessageService messageService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String userName = message.getFrom().getUserName();
            List<User> newChatMembers = message.getNewChatMembers();
            newChatMembers.forEach(memb -> userService.createUser(new org.telran.project.telegrambot.model.User(memb.getUserName())));
            Chat chat = message.getChat();
            if (chat.isGroupChat()) {
                String title = chat.getTitle();
                Long id = chat.getId();
                if (channelService.getChannel(id) == null) {
                    channelService.createChannel(new Channel(title, id));
                }
                String text = message.getText();
                Integer messageId = message.getMessageId();
                messageService.createMessage(new TelegramMessage((int)messageId, title, id, text));
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "projectBot_telranbot";
    }

    @Override
    public String getBotToken() {
        return "";
    }

}
