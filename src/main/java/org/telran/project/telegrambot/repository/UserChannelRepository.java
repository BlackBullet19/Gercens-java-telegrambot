package org.telran.project.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.telran.project.telegrambot.model.UserChannel;

import java.util.List;

@Repository
public interface UserChannelRepository extends JpaRepository<UserChannel, Integer> {

    /**
     * Returns all user subscriptions by internal user identifier
     *
     * @param userId must not be 0
     * @return A list of UserChannels(user subscriptions) entities
     */
    @Query("SELECT uc FROM UserChannel uc WHERE uc.userId = :userId")
    List<UserChannel> findByUserId(@Param("userId") int userId);

    /**
     * Returns specific, unique subscription by user identifier and channel identifier
     *
     * @param userId    user identifier, must not be 0
     * @param channelId channel/group internal identifier, must not be 0
     * @return Returns UserChannel entity
     */
    @Query("SELECT uc FROM UserChannel uc WHERE uc.userId = :userId AND uc.channelId = :channelId")
    UserChannel findByUserIdAndChannelId(@Param("userId") int userId, @Param("channelId") int channelId);

    /**
     * Returns boolean value depending on is subscription with given identifiers present or not
     *
     * @param userId    user identifier, not 0
     * @param channelId channel internal identifier, not 0
     * @return true if present, false if not
     */
    boolean existsByUserIdAndChannelId(int userId, int channelId);

    /**
     * Returns list of UserChannel entities which have match from list of channel internal identifiers
     *
     * @param channelIds list of channel internal identifiers
     * @return list of entities
     */
    @Query("SELECT uc FROM UserChannel uc WHERE uc.channelId IN :channelIds")
    List<UserChannel> findAllUserChannelsByChannelIdFromIdsList(@Param("channelIds") List<Integer> channelIds);
}
