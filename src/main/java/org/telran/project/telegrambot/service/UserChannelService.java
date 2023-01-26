package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.UserChannel;

import java.util.List;

public interface UserChannelService {

    List<UserChannel> listUserChannels(int userId);

    UserChannel addUserSubscription(int userId, UserChannel userChannel);

    void removeUserSubscription(int userId, int channelId);

    List<UserChannel> listAll();

    UserChannel createUserChannelWithoutSavingToRepository(int userId, int channelId);

    void saveAllUserSubscriptions(List<UserChannel> list);

    boolean existsByUserIdAndChannelId(int userId, int channelId);

    List<UserChannel> findAllUserChannelsByChannelIdFromIdsList(List<Integer> channelIds);

}
