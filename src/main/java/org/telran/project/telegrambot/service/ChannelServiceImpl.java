package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.repository.ChannelRepository;

import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public Channel getChannel(long id) {
        return channelRepository.findById(id);
    }

    @Override
    public void createChannel(Channel channel) {
        channelRepository.save(channel);
    }

    @Override
    public void deleteChannel(long id) {
        Channel channel = getChannel(id);
        channelRepository.delete(channel);

    }

    @Override
    public Channel updateChannel(long id) {
        return getChannel(id);
    }

    @Override
    public List<Channel> listChannels() {
        return channelRepository.findAll();
    }
}
