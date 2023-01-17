package org.telran.project.telegrambot.service;


import org.telran.project.telegrambot.model.Channel;

import java.util.List;

public interface ChannelService {

    Channel getChannel(long id);

    void createChannel(Channel channel);

    void deleteChannel(long id);

    Channel updateChannel(long id);

    List<Channel> listChannels();
}
