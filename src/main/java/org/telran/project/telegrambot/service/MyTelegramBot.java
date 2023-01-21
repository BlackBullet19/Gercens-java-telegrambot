package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;


@Service
@PropertySource("bot.properties")
public class MyTelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private UserChannelService userChannelService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CommandService commandService;

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasChannelPost()) {
            Message channelPost = update.getChannelPost();
            if (channelPost.isCommand()) {
                commandService.executeCommand(channelPost.getText(), channelPost.getChatId());
            }
            Chat chat = channelPost.getChat();
            if (chat.isChannelChat()) {
                long id = chat.getId();
                String title = chat.getTitle();
                if (channelService.getChannel(id) == null) {
                    channelService.createChannel(title, id);
                }
                String text = channelPost.getText();
                Integer messageId = channelPost.getMessageId();
                if (channelPost.hasText() && !channelPost.isCommand()) {
                    if (channelService.getChannel(id).isBotEnabled()) {
                        messageService.createMessage(messageId, title, id, text);
                    }
                }
            }
        }

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand()) {
                commandService.executeCommand(message.getText(), message.getChatId());
            }
                List<User> newChatMembers = message.getNewChatMembers();
                newChatMembers.forEach(member -> userService.createUser(member.getUserName(), member.getId()));
                Chat chat = message.getChat();
                if (chat.isGroupChat() || chat.isSuperGroupChat()) {
                    String title = chat.getTitle();
                    long id = chat.getId();
                    if (channelService.getChannel(id) == null) {
                        channelService.createChannel(title, id);
                    }
                    String text = message.getText();
                    Integer messageId = message.getMessageId();
                    if (message.hasText() && !message.isCommand()) {
                        if (channelService.getChannel(id).isBotEnabled()) {
                            messageService.createMessage(messageId, title, id, text);
                        }
                }
                newChatMembers.forEach(m -> {
                    int userId = userService.getUserByUserId(m.getId()).getId();
                    userChannelService.addUserSubscription(userId, chat.getId());
                });
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
