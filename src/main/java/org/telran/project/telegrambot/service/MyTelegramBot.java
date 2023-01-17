package org.telran.project.telegrambot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;



@Service
public class MyTelegramBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Chat chat = message.getChat();
        if(chat.isGroupChat()){
            String title = chat.getTitle();
            Long id = chat.getId();
            String text = message.getText();

        }
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

}
