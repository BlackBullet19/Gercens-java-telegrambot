package org.telran.project.telegrambot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.UserChannel;
import org.telran.project.telegrambot.repository.UserChannelRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserChannelServiceImpl implements UserChannelService {

    @Autowired
    private UserChannelRepository userChannelRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Override
    public List<UserChannel> listUserChannels(int userId) {
        if (userChannelRepository.existsByUserId(userId)) {
            return userChannelRepository.findByUserId(userId);
        }
        throw new IllegalArgumentException("userId is not valid");
    }

    @Override
    public void removeUserSubscription(int userId, int channelId) {
        if (!userChannelRepository.existsByUserIdAndChannelId(userId, channelId)) {
            throw new IllegalArgumentException("userId or channelId is not valid");
        }
        if (userChannelRepository.existsByUserIdAndChannelId(userId, channelId)) {
            UserChannel userChannelForDeleting = userChannelRepository
                    .findByUserIdAndChannelId(userId, channelId);
            userChannelRepository.delete(userChannelForDeleting);
        }
    }

    @Override
    public UserChannel addUserSubscription(int userId, UserChannel userChannel) {
        if (!userService.existsById(userId)) {
            throw new NoSuchElementException("There are not user with this id");
        }
        if (!channelService.existsById(userChannel.getChannelId())) {
            throw new NoSuchElementException("There are not channel with this id");
        }
        if (userChannelRepository.existsByUserIdAndChannelId(userId, userChannel.getChannelId())) {
            throw new IllegalStateException("Subscription already exists");
       }
        return userChannelRepository.save(new UserChannel(userId, userChannel.getChannelId()));
    }

    @Override
    public List<UserChannel> listAll() {
        return userChannelRepository.findAll();
    }

    @Override
    public UserChannel createUserChannelWithoutSavingToRepository(int userId, int channelId) {
        if (userId != 0 && channelId != 0) {
            return new UserChannel(userId, channelId);
        }
        throw new IllegalArgumentException("userId or channelId is not valid");
    }

    @Override
    public void saveAllUserSubscriptions(List<UserChannel> list) {
        userChannelRepository.saveAll(list);
    }

    @Override
    public boolean existsByUserIdAndChannelId(int userId, int channelId) {
        return userChannelRepository.existsByUserIdAndChannelId(userId, channelId);
    }

    @Override
    public List<UserChannel> findAllUserChannelsByChannelIdFromIdsList(List<Integer> channelIds) {
        return userChannelRepository.findAllUserChannelsByChannelIdFromIdsList(channelIds);
    }
}
