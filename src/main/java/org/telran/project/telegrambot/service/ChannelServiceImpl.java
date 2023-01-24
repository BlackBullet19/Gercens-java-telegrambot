package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.repository.ChannelRepository;

import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {

    public ChannelServiceImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public ChannelServiceImpl() {
    }

    @Autowired
    private ChannelRepository channelRepository;


    @Override
    public Channel getChannel(long channelId) {
        if (channelRepository.existsByChannelId(channelId)) {
            return channelRepository.findByChannelId(channelId);
        }
        return null;
    }

    @Override
    public Channel createChannel(Channel channel) {
       return channelRepository.save(channel);
    }

    @Override
    public void deleteChannel(long id) {
        if (channelRepository.existsByChannelId(id)) {
            Channel channel = channelRepository.findByChannelId(id);
            channelRepository.delete(channel);
        }
    }

    @Override
    public Channel updateChannel(long id) {
        if (channelRepository.existsByChannelId(id)) {
            return channelRepository.findByChannelId(id);
        }
        return null;
    }

    @Override
    public List<Channel> listChannels() {
        return channelRepository.findAll();
    }

    @Override
    public Channel createChannel(String name, long channelId) {
       return channelRepository.save(new Channel(name, channelId));
    }

    @Override
    public void on(long channelId) {
        channelRepository.changeIsBotEnabledToTrue(channelId);
    }

    @Override
    public void off(long channelId) {
        channelRepository.changeIsBotEnabledToFalse(channelId);
    }
}
