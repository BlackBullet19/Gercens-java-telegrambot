package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.Channel;

import java.util.List;

/**
 * This service is main interface to implement to work with Channel entities,
 * for example get, create, remove or update
 *
 * @author Olegs Gercens
 */
public interface ChannelService extends Switchable {

    /**
     * Returns Channel entity by specific identifier
     *
     * @param id channel identifier
     * @return Channel entity by specific identifier
     */
    Channel getChannel(int id);

    /**
     * Returns Channel entity by specific external identifier
     *
     * @param id channel external identifier
     * @return Channel entity by specific external identifier
     */
    Channel getChannelByChannelId(long id);

    /**
     * Saves new Channel entity and returns it
     *
     * @param channel - entity to save
     * @return Returns entity
     */
    Channel createChannel(Channel channel);

    /**
     * Removes Channel entity by specific id
     *
     * @param id - channel specific identifier
     */
    void deleteChannel(int id);

    /**
     * Updates data for specific Channel entity using data from entity given ir arguments
     *
     * @param id      - specific channel identifier
     * @param channel - entity containing changes
     * @return Existing Channel entity with changes made
     */
    Channel updateChannel(int id, Channel channel);

    /**
     * Returns a list of all Channel entities
     *
     * @return a list of all Channel entities
     */
    List<Channel> listChannels();

    /**
     * Returns true if database contains Channel entity with specific identifier given
     *
     * @param id specific channel identifier
     * @return true if database contains Channel entity with specific identifier
     */
    boolean existsById(int id);

    /**
     * Returns a list of internal identifiers of Channel entities which have match with those channel
     * external ids from given list
     *
     * @param channelIds list of external Channel identifiers
     * @return a list of internal identifiers
     */
    List<Integer> findAllIdsByChannelIdFromUniqueChannelIdsList(List<Long> channelIds);
}
