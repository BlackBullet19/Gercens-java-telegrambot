package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MyTelegramBot extends TelegramLongPollingBot {

    private static final String DEFAULT_BOT_NAME = "";
    private static final String DEFAULT_BOT_TOKEN = "";

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CommandService commandService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasChannelPost()) {
            Message channelPost = update.getChannelPost();
            messageDataProcessing(channelPost);
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            messageDataProcessing(message);
        }
    }

    private void messageDataProcessing(Message message) {
        Chat chat = message.getChat();
        if (message.isCommand()) {
            commandService.executeCommand(message.getText(), message.getChatId());
        }
        if (chat.isGroupChat() || chat.isSuperGroupChat() || chat.isChannelChat()) {
            saveMessage(message, chat);
        }
    }

    private void saveMessage(Message message, Chat chat) {
        if (message.hasText() && !message.isCommand()) {
            String text = message.getText();
            if (channelService.getChannelByChannelId(chat.getId()).isBotEnabled()) {
                messageService.createMessage(chat.getTitle(), chat.getId(), text);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName.isEmpty() ? DEFAULT_BOT_NAME : botName;
    }

    @Override
    public String getBotToken() {
        return botToken.isEmpty() ? DEFAULT_BOT_TOKEN : botToken;
    }
}
