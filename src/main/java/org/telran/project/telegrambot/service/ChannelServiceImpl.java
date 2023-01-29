package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.repository.ChannelRepository;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of ChannelService interface, to work with Channel entities,
 * for example get, create, remove or update
 *
 * @author Olegs Gercens
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    /**
     * Returns channel entity by specific identifier
     *
     * @param id channel identifier
     * @return channel entity by specific identifier
     * @throws IllegalArgumentException - if identifier is 0
     * @throws NoSuchElementException   - if channel not found
     */
    @Override
    public Channel getChannel(int id) {
        if (id == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (channelRepository.existsById(id)) {
            return channelRepository.findById(id).get();
        }
        throw new NoSuchElementException("Channel with value " + id + " not found");
    }

    /**
     * Returns channel entity by specific external identifier
     *
     * @param channelId channel external identifier
     * @return channel entity by specific identifier
     * @throws IllegalArgumentException - if identifier is 0
     * @throws NoSuchElementException   - if channel not found
     */
    @Override
    public Channel getChannelByChannelId(long channelId) {
        if (channelId == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (channelRepository.existsByChannelId(channelId)) {
            return channelRepository.findByChannelId(channelId);
        }
        throw new NoSuchElementException("Channel with ID " + channelId + " not found");
    }

    /**
     * Registers to save new Channel entity
     *
     * @param channel - entity to save
     * @return channel entity
     * @throws InvalidParameterException     - if at least one channel parameter was 0 or empty
     * @throws UnsupportedOperationException - if Channel with entered parameters already exists
     */
    @Override
    public Channel createChannel(Channel channel) {
        if (channel.getName() == null || channel.getChannelId() == 0) {
            throw new InvalidParameterException("At least one entered parameter was 0 or empty");
        }
        if (channelRepository.existsByChannelId(channel.getChannelId())) {
            throw new UnsupportedOperationException("Channel with entered parameters already exists");
        }
        Channel newChannel = new Channel(channel.getName(), channel.getChannelId());
        return channelRepository.save(newChannel);
    }

    /**
     * Remove Channel entity by specific identifier
     *
     * @param id - channel specific identifier
     * @throws IllegalArgumentException - if identifier is 0
     * @throws NoSuchElementException   - if channel not found
     */
    @Override
    public void deleteChannel(int id) {
        if (id == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (!channelRepository.existsById(id)) {
            throw new NoSuchElementException("Channel with ID " + id + " not found");
        }
        if (channelRepository.existsById(id)) {
            Channel channel = channelRepository.findById(id).get();
            channelRepository.delete(channel);
        }
    }

    /**
     * Updates data for specific Channel entity using data from entity given ir arguments
     *
     * @param id      - specific channel identifier
     * @param channel - entity containing changes
     * @return Existing Channel entity with changes made
     * @throws IllegalArgumentException  - if identifier is 0
     * @throws NoSuchElementException    - if channel not found
     * @throws InvalidParameterException - if at least one entered parameter was 0 or empty
     */
    @Override
    public Channel updateChannel(int id, Channel channel) {
        if (id == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (!channelRepository.existsById(id)) {
            throw new NoSuchElementException("Channel with ID " + id + " not found");
        }
        if (channel.getName() == null || channel.getChannelId() == 0) {
            throw new InvalidParameterException("At least one entered parameter was 0 or empty");
        }
        Channel existingChannel = channelRepository.findById(id).get();
        existingChannel.setChannelId(channel.getChannelId());
        existingChannel.setName(channel.getName());
        return channelRepository.save(existingChannel);
    }

    /**
     * Returns a list of all Channel entities
     *
     * @return a list of all Channel entities
     */
    @Override
    public List<Channel> listChannels() {
        return channelRepository.findAll();
    }

    /**
     * Changes a value of isBotEnabled to true for specific Channel by external identifier
     *
     * @param channelId - external identifier
     * @throws IllegalArgumentException - if external identifier was 0
     * @throws NoSuchElementException   - if Channel not found
     */
    @Override
    public void on(long channelId) {
        if (channelId == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (!channelRepository.existsByChannelId(channelId)) {
            throw new NoSuchElementException("Channel with ChannelId " + channelId + " not found");
        }
        channelRepository.changeIsBotEnabledToTrue(channelId);
    }

    /**
     * Changes a value of isBotEnabled to false for specific Channel by external identifier
     *
     * @param channelId - external identifier
     * @throws IllegalArgumentException - if external identifier was 0
     * @throws NoSuchElementException   - if Channel not found
     */
    @Override
    public void off(long channelId) {
        if (channelId == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (!channelRepository.existsByChannelId(channelId)) {
            throw new NoSuchElementException("Channel with ChannelId " + channelId + " not found");
        }
        channelRepository.changeIsBotEnabledToFalse(channelId);
    }

    /**
     * Returns true if specific Channel is present by identifier
     *
     * @param id channel identifier
     * @return true if specific Channel is present
     * @throws IllegalArgumentException - if identifier was 0
     */
    @Override
    public boolean existsById(int id) {
        if (id == 0) throw new IllegalArgumentException("Entered value can not be 0");
        return channelRepository.existsById(id);
    }

    /**
     * Returns a list of internal identifiers of Channel entities which have match with those channel
     * external ids from given list
     *
     * @param channelIds list of external Channel identifiers
     * @return a list of internal identifiers
     */
    @Override
    public List<Integer> findAllIdsByChannelIdFromUniqueChannelIdsList(List<Long> channelIds) {
        return channelRepository.findAllIdsByChannelIdFromUniqueChannelIdsList(channelIds);
    }
}
