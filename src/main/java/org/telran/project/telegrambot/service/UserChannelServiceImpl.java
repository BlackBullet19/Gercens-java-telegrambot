package org.telran.project.telegrambot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.model.UserChannel;
import org.telran.project.telegrambot.repository.UserChannelRepository;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class UserChannelServiceImpl implements UserChannelService {

    @Autowired
    private UserChannelRepository userChannelRepository;

    @Override
    public List<UserChannel> listUserChannels(int userId) {
        if (userChannelRepository.existsByUserId(userId)) {
            return userChannelRepository.findByUserId(userId);
        }
        throw new InvalidParameterException();
    }

    @Override
    public void addUserSubscription(int userId, Channel channel) {
        userChannelRepository.save(new UserChannel(userId, channel.getChannelId()));
    }

    @Override
    public void removeUserSubscription(int userId, Channel channel) {
        UserChannel userChannel = userChannelRepository.findByUserIdAndChannelId(userId, channel.getChannelId());
        userChannelRepository.delete(userChannel);
    }

    @Override
    public List<Integer> findAllUsersByChannelId(long channelId) {
        return userChannelRepository.findAllUserIdByChannelId(channelId);
    }

    @Override
    public void addUserSubscription(int userId, long channelId) {
        userChannelRepository.save(new UserChannel(userId, channelId));
    }
}
