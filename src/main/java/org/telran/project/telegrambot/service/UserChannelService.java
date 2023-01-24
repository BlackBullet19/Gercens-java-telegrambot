package org.telran.project.telegrambot.service;


import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.model.User;
import org.telran.project.telegrambot.model.UserChannel;

import java.util.List;

public interface UserChannelService {

    List<UserChannel> listUserChannels(int userId);

    void addUserSubscription(int userId, Channel channel);

    void removeUserSubscription(int userId, long channelId);

    void addUserSubscription(int userId, long channelId);

    List<UserChannel> listAll();

    UserChannel createUserChannelWithoutSavingToRepository(int userId, long channelId);

    void saveAllUsers(List<UserChannel> list);

}
