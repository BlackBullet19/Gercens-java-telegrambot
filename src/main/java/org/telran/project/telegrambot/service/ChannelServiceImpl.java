package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.repository.ChannelRepository;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ChannelServiceImpl implements ChannelService {


    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public Channel getChannel(int id) {
        if (channelRepository.existsById(id)) {
            return channelRepository.findById(id).get();
        }
        throw new IllegalArgumentException("Invalid identifier");
    }

    @Override
    public Channel getChannelByChannelId(long channelId) {
        if (channelRepository.existsByChannelId(channelId)) {
            return channelRepository.findByChannelId(channelId);
        }
        throw new IllegalArgumentException("Invalid channel identifier");
    }

    @Override
    public Channel createChannel(Channel channel) {
        if (channel.getName() == null || channel.getChannelId() == 0) {
            throw new IllegalArgumentException("Parameters are not valid");
        }
        if (channelRepository.existsByChannelId(channel.getChannelId())) {
            throw new IllegalStateException("Channel already exists");
        }
        Channel newChannel = new Channel(channel.getName(), channel.getChannelId());
        return channelRepository.save(newChannel);
    }

    @Override
    public void deleteChannel(int id) {
        if(!channelRepository.existsById(id)){
            throw new IllegalArgumentException("Invalid identifier");
        }
        if (channelRepository.existsById(id)) {
            Channel channel = channelRepository.findById(id).get();
            channelRepository.delete(channel);
        }
    }

    @Override
    public Channel updateChannel(int id, Channel channel) {
        if (channelRepository.existsById(id)) {
            Channel existingChannel = channelRepository.findById(id).get();
            if (channel != null) {
                existingChannel.setChannelId(channel.getChannelId());
                existingChannel.setName(channel.getName());
            }
            return channelRepository.save(existingChannel);
        }
        throw new InvalidParameterException("Parameters are not valid");
    }

    @Override
    public List<Channel> listChannels() {
        return channelRepository.findAll();
    }

    @Override
    public Channel createChannel(String name, long channelId) {
        if (name == null || channelId == 0) {
            throw new IllegalArgumentException("Parameters are not valid");
        }
        if (channelRepository.existsByChannelId(channelId)) {
            throw new IllegalStateException("Channel already exists");
        }
        Channel newChannel = new Channel(name, channelId);
        return channelRepository.save(newChannel);
    }

    @Override
    public void on(long channelId) {
        if (channelRepository.existsByChannelId(channelId)) {
            channelRepository.changeIsBotEnabledToTrue(channelId);
        }
        throw new NoSuchElementException("Channel with this identifier not found");
    }

    @Override
    public void off(long channelId) {
        if (channelRepository.existsByChannelId(channelId)) {
            channelRepository.changeIsBotEnabledToFalse(channelId);
        }
        throw new NoSuchElementException("Channel with this identifier not found");
    }

    @Override
    public boolean existsByChannelId(long channelId) {
       return channelRepository.existsByChannelId(channelId);
    }

    @Override
    public boolean existsById(int id) {
        return channelRepository.existsById(id);
    }

    @Override
    public List<Integer> findAllIdsByChannelIdFromUniqueChannelIdsList(List<Long> channelIds) {
        return channelRepository.findAllIdsByChannelIdFromUniqueChannelIdsList(channelIds);
    }
}
