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
import org.telran.project.telegrambot.model.UserChannel;

import java.util.ArrayList;
import java.util.List;


@Service
@PropertySource("bot.properties")
public class MyTelegramBot extends TelegramLongPollingBot {

    private static final String DEFAULT_BOT_NAME = "";

    private static final String DEFAULT_BOT_TOKEN = "";

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
            messageDataProcessing(channelPost);
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            messageDataProcessing(message);
            List<User> newChatMembers = message.getNewChatMembers();
            if (newChatMembers != null) {
                List<org.telran.project.telegrambot.model.User> list = userService.list();
                saveNewUsers(newChatMembers, list);
                saveNewUserChannelSubscriptions(newChatMembers, message.getChat(), list);
            }
        }
    }

    private void messageDataProcessing(Message message) {
        Chat chat = message.getChat();
        if (message.isCommand()) {
            commandService.executeCommand(message.getText(), message.getChatId());
        }
        if (chat.isGroupChat() || chat.isSuperGroupChat() || chat.isChannelChat()) {
            saveChannelOrGroup(chat);
            saveMessage(message, chat);
        }
    }

    private void saveChannelOrGroup(Chat chat) {
        String title = chat.getTitle();
        long id = chat.getId();
        if (channelService.getChannel(id) == null) {
            channelService.createChannel(title, id);
        }
    }

    private void saveMessage(Message message, Chat chat) {
        String text = message.getText();
        Integer messageId = message.getMessageId();
        if (message.hasText() && !message.isCommand()) {
            if (channelService.getChannel(chat.getId()).isBotEnabled()) {
                messageService.createMessage(messageId, chat.getTitle(), chat.getId(), text);
            }
        }
    }

    private void saveNewUserChannelSubscriptions(List<User> newChatMembers, Chat chat,
                                                 List<org.telran.project.telegrambot.model.User> userList) {
        List<UserChannel> userChannels = userChannelService.listAll();
        List<UserChannel> newSubscriptions = new ArrayList<>();
        newChatMembers.forEach(m -> {
            org.telran.project.telegrambot.model.User userByUserId =
                    userList.stream().filter(user -> user.getUserId() == m.getId()).findFirst().orElse(null);
            if (userByUserId != null) {
                int userIntId = userByUserId.getId();
                UserChannel newUserChannel = userChannelService
                        .createUserChannelWithoutSavingToRepository(userIntId, chat.getId());
                if (!userChannels.contains(newUserChannel)) {
                    newSubscriptions.add(newUserChannel);
                }
            }
        });
        userChannelService.saveAllUsers(newSubscriptions);
    }

    private void saveNewUsers(List<User> newChatMembers, List<org.telran.project.telegrambot.model.User> list) {
        List<org.telran.project.telegrambot.model.User> myNewUserList = new ArrayList<>();
        newChatMembers.forEach(member -> {
            org.telran.project.telegrambot.model.User newUser =
                    userService.createUserWithoutSavingToRepository(member.getUserName(), member.getId());
            if (!list.contains(newUser)) {
                myNewUserList.add(newUser);
            }
        });
        userService.saveAllUsers(myNewUserList);
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
