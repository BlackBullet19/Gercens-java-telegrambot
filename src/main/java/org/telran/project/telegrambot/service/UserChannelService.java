package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.UserChannel;

import java.util.List;

/**
 * UserChannelService interface to implement to work with user subscriptions,
 * have basic CRUD operations
 *
 * @author Olegs Gercens
 */
public interface UserChannelService {

    /**
     * Returns a list of specific users subscriptions
     *
     * @param userId internal user identifier
     * @return a list of specific users subscriptions
     */
    List<UserChannel> listUserChannels(int userId);

    /**
     * Registers to save specified subscription for specific user, returns UserChannel entity
     *
     * @param userId      internal user identifier
     * @param userChannel entity to save
     * @return UserChannel entity
     */
    UserChannel addUserSubscription(int userId, UserChannel userChannel);

    /**
     * Deletes specific subscription for specific user
     *
     * @param userId    internal user identifier
     * @param channelId internal channel/group identifier
     */
    void removeUserSubscription(int userId, int channelId);

    /**
     * Returns a list of all subscriptions to specific channels
     *
     * @param channelIds a list of internal channel/group identifiers
     * @return a list of all subscriptions to specific channels
     */
    List<UserChannel> findAllUserChannelsByChannelIdFromIdsList(List<Integer> channelIds);

    /**
     * Activates specific subscription for sending events and returns this UserChannel entity
     *
     * @param userId      internal user identifier
     * @param userChannel internal channel/group identifier
     * @return activated UserChannel entity
     */
    UserChannel activateSubscription(int userId, UserChannel userChannel);

    /**
     * Deactivates specific subscription for sending events and returns this UserChannel entity
     *
     * @param userId      internal user identifier
     * @param userChannel internal channel/group identifier
     * @return deactivated UserChannel entity
     */
    UserChannel deactivateSubscription(int userId, UserChannel userChannel);
}
