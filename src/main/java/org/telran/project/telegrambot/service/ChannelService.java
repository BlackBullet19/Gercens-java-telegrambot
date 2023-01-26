package org.telran.project.telegrambot.service;


import org.telran.project.telegrambot.model.Channel;

import java.util.List;


public interface ChannelService extends Switchable{

    Channel getChannel(int id);

    Channel getChannelByChannelId(long id);

    Channel createChannel(Channel channel);

    void deleteChannel(int id);

    Channel updateChannel(int id, Channel channel);

    List<Channel> listChannels();

    Channel createChannel(String name, long channelId);

    boolean existsByChannelId(long channelId);

    boolean existsById(int id);

    List<Integer> findAllIdsByChannelIdFromUniqueChannelIdsList (List<Long> channelIds);
}
