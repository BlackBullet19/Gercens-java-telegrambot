package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.UserChannel;
import org.telran.project.telegrambot.repository.UserChannelRepository;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * UserChannelService implementation to work with user subscriptions,
 * have basic CRUD operations
 *
 * @author Olegs Gercens
 */
@Service
public class UserChannelServiceImpl implements UserChannelService {

    @Autowired
    private UserChannelRepository userChannelRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    /**
     * Returns a list of specific users subscriptions by user identifier
     *
     * @param userId internal user identifier
     * @return a list of specific users subscriptions
     * @throws IllegalArgumentException - if user identifier was 0
     * @throws NoSuchElementException   - if user not found
     */
    @Override
    public List<UserChannel> listUserChannels(int userId) {
        if (userId == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (userService.existsById(userId)) {
            return userChannelRepository.findByUserId(userId);
        }
        throw new NoSuchElementException("User with ID " + userId + " not found");
    }

    /**
     * Deletes specific subscription for specific user
     *
     * @param userId    internal user identifier
     * @param channelId internal channel/group identifier
     * @throws IllegalArgumentException - if at least one entered parameter was 0
     * @throws NoSuchElementException   - if subscription not found
     */
    @Override
    public void removeUserSubscription(int userId, int channelId) {
        if (userId == 0 || channelId == 0) throw new IllegalArgumentException("At least one entered parameter was 0");
        if (!userChannelRepository.existsByUserIdAndChannelId(userId, channelId)) {
            throw new NoSuchElementException
                    ("Subscription with ChannelId " + channelId + " and UserId " + userId + " not found");
        }
        if (userChannelRepository.existsByUserIdAndChannelId(userId, channelId)) {
            UserChannel userChannelForDeleting = userChannelRepository
                    .findByUserIdAndChannelId(userId, channelId);
            userChannelRepository.delete(userChannelForDeleting);
        }
    }

    /**
     * Registers to save specified subscription for specific user, returns UserChannel entity
     *
     * @param userId      internal user identifier
     * @param userChannel entity to save
     * @return UserChannel entity
     * @throws IllegalArgumentException      - if user identifier was 0
     * @throws NoSuchElementException        - if user not found
     * @throws InvalidParameterException     - if at least one entered parameter was 0
     * @throws InvalidParameterException     - if user identifier is not same as user identifier in entity to save
     * @throws NoSuchElementException        - if channel not found
     * @throws UnsupportedOperationException - if subscription already exists
     */
    @Override
    public UserChannel addUserSubscription(int userId, UserChannel userChannel) {
        if (userId == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (userChannel.getUserId() == 0 || userChannel.getChannelId() == 0) {
            throw new InvalidParameterException("At least one entered parameter was 0");
        }
        if (!userService.existsById(userId)) {
            throw new NoSuchElementException("User with ID " + userId + " not found");
        }
        if (userId != userChannel.getUserId()) {
            throw new InvalidParameterException
                    ("User ID " + userId + " must be same as entered one " + userChannel.getUserId());
        }
        if (!channelService.existsById(userChannel.getChannelId())) {
            throw new NoSuchElementException("Channel ID " + userChannel.getChannelId() + " not found");
        }
        if (userChannelRepository.existsByUserIdAndChannelId(userId, userChannel.getChannelId())) {
            throw new UnsupportedOperationException("Subscription with entered parameters already exists");
        }
        return userChannelRepository.save(new UserChannel(userId, userChannel.getChannelId()));
    }

    /**
     * Returns a list of all subscriptions to specific channels
     *
     * @param channelIds a list of internal channel/group identifiers
     * @return a list of all subscriptions to specific channels
     */
    @Override
    public List<UserChannel> findAllUserChannelsByChannelIdFromIdsList(List<Integer> channelIds) {
        return userChannelRepository.findAllUserChannelsByChannelIdFromIdsList(channelIds);
    }

    /**
     * Activates specific subscription for sending events and returns this UserChannel entity
     *
     * @param userId      internal user identifier
     * @param userChannel internal channel/group identifier
     * @return activated UserChannel entity
     * @throws IllegalArgumentException  - if user identifier was 0
     * @throws NoSuchElementException    - if user not found
     * @throws InvalidParameterException - if at least one entered parameter was 0
     * @throws InvalidParameterException - if user identifier is not same as user identifier in entity to save
     * @throws NoSuchElementException    - if subscription not found
     */
    @Override
    public UserChannel activateSubscription(int userId, UserChannel userChannel) {
        if (userId == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (userChannel.getUserId() == 0 || userChannel.getChannelId() == 0) {
            throw new InvalidParameterException("At least one entered parameter was 0");
        }
        if (!userService.existsById(userId)) {
            throw new NoSuchElementException("User with ID " + userId + " not found");
        }
        if (userId != userChannel.getUserId()) {
            throw new InvalidParameterException
                    ("User ID " + userId + " must be same as entered one " + userChannel.getUserId());
        }
        if (!userChannelRepository.existsByUserIdAndChannelId(userId, userChannel.getChannelId())) {
            throw new NoSuchElementException("Subscription with entered parameters doesn't exist");
        }
        UserChannel existingUser = userChannelRepository.findByUserIdAndChannelId(userId, userChannel.getChannelId());
        existingUser.setSubscribed(true);
        return userChannelRepository.save(existingUser);
    }

    /**
     * Deactivates specific subscription for sending events and returns this UserChannel entity
     *
     * @param userId      internal user identifier
     * @param userChannel internal channel/group identifier
     * @return deactivated UserChannel entity
     * @throws IllegalArgumentException  - if user identifier was 0
     * @throws NoSuchElementException    - if user not found
     * @throws InvalidParameterException - if at least one entered parameter was 0
     * @throws InvalidParameterException - if user identifier is not same as user identifier in entity to save
     * @throws NoSuchElementException    - if subscription not found
     */
    @Override
    public UserChannel deactivateSubscription(int userId, UserChannel userChannel) {
        if (userId == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (userChannel.getChannelId() == 0 || userChannel.getUserId() == 0) {
            throw new InvalidParameterException("At least one entered parameter was 0");
        }
        if (!userService.existsById(userId)) {
            throw new NoSuchElementException("User with ID " + userId + " not found");
        }
        if (userId != userChannel.getUserId()) {
            throw new InvalidParameterException
                    ("User ID " + userId + " must be same as entered one " + userChannel.getUserId());
        }
        if (!userChannelRepository.existsByUserIdAndChannelId(userId, userChannel.getChannelId())) {
            throw new NoSuchElementException("Subscription with entered parameters doesn't exist");
        }
        UserChannel existingUser = userChannelRepository.findByUserIdAndChannelId(userId, userChannel.getChannelId());
        existingUser.setSubscribed(false);
        return userChannelRepository.save(existingUser);
    }
}
