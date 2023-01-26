package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telran.project.telegrambot.model.User;
import org.telran.project.telegrambot.model.UserChannel;

import java.util.ArrayList;
import java.util.List;


@Service
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
            channelPost.getChatId();
            messageDataProcessing(channelPost);
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            messageDataProcessing(message);
            if (message.getNewChatMembers() != null) {
                List<org.telran.project.telegrambot.model.User> list = userService.list();
                saveNewUsers(message, list);
            }
        }
    }

    private void messageDataProcessing(Message message) {
        Chat chat = message.getChat();
        if (message.isCommand()) {
            commandService.executeCommand(message.getText(), message.getChatId());
        }
        if (chat.isGroupChat() || chat.isSuperGroupChat() || chat.isChannelChat()) {
            if (!channelService.existsByChannelId(chat.getId())) {
                channelService.createChannel(chat.getTitle(), chat.getId());
            }
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

    private void saveNewUserChannelSubscriptions(Message message,
                                                 List<org.telran.project.telegrambot.model.User> userList) {

        List<UserChannel> userChannels = userChannelService.listAll();
        int channelId = channelService.getChannelByChannelId(message.getChatId()).getId();
        List<UserChannel> newSubscriptions = new ArrayList<>();

        message.getNewChatMembers().forEach(m -> {
            org.telran.project.telegrambot.model.User userByUserTelegramId =
                    userList.stream().filter(user -> user.getUserId() == m.getId()).findFirst().get();

            int userId = userByUserTelegramId.getId();
            UserChannel newUserChannel = userChannelService
                    .createUserChannelWithoutSavingToRepository(userId, channelId);
            if (!userChannels.contains(newUserChannel)) {
                newSubscriptions.add(newUserChannel);

            }
            userChannelService.saveAllUserSubscriptions(newSubscriptions);
        });
    }

    private void saveNewUsers(Message message, List<org.telran.project.telegrambot.model.User> list) {
        List<org.telran.project.telegrambot.model.User> myNewUserList = new ArrayList<>();
        message.getNewChatMembers().forEach(member -> {
            org.telran.project.telegrambot.model.User newUser =
                    userService.createUserWithoutSavingToRepository(member.getUserName(), member.getId());
            if (!list.contains(newUser)) {
                myNewUserList.add(newUser);
            }
        });
        List<User> userList = userService.saveAllUsers(myNewUserList);
        saveNewUserChannelSubscriptions(message, userList);
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
