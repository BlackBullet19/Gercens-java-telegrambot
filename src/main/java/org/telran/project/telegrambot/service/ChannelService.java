package org.telran.project.telegrambot.service;


import org.telran.project.telegrambot.model.Channel;

import java.util.List;

public interface ChannelService extends Switchable{

    Channel getChannel(long id);

    Channel createChannel(Channel channel);

    void deleteChannel(long id);

    Channel updateChannel(long id);

    List<Channel> listChannels();

    Channel createChannel(String name, long channelId);
}
