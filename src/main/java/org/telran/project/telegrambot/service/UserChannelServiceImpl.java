package org.telran.project.telegrambot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.model.UserChannel;
import org.telran.project.telegrambot.repository.ChannelRepository;
import org.telran.project.telegrambot.repository.UserChannelRepository;

import java.util.List;

@Service
public class UserChannelServiceImpl implements UserChannelService {

    @Autowired
    private UserChannelRepository userChannelRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public List<UserChannel> listUserChannels(int userId) {
        return userChannelRepository.findByUserId(userId);
    }

    @Override
    public void addUserSubscription(int userId, Channel channel) {
        userChannelRepository.save(new UserChannel(userId,channel.getChannelId()));
    }

    @Override
    public void removeUserSubscription(int userId, Channel channel) {
        UserChannel userChannel = userChannelRepository.findByUserIdAndChannelId(userId, channel.getChannelId());
        userChannelRepository.delete(userChannel);
    }
}
