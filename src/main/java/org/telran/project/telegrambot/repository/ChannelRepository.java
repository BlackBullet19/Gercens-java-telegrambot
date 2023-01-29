package org.telran.project.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.telran.project.telegrambot.model.Channel;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {

    /**
     * Return an Channel entity with given channel identifier
     *
     * @param channelId must not be null
     * @return Channel entity
     */
    Channel findByChannelId(long channelId);

    /**
     * Return a boolean value depending on is Channel entity with given channel identifier
     * present or not
     *
     * @param channelId must not be null
     * @return true/false
     */
    boolean existsByChannelId(long channelId);

    /**
     * Change a value of isBotEnabled to false for specific
     * {@link org.telran.project.telegrambot.model.Channel} by given channelId
     *
     * @param channelId must not be null
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Channel ch SET ch.isBotEnabled = false WHERE ch.channelId = :channelId ")
    void changeIsBotEnabledToFalse(@Param("channelId") long channelId);

    /**
     * Change a value of isBotEnabled to true for specific
     * {@link org.telran.project.telegrambot.model.Channel} by given channelId
     *
     * @param channelId must not be null
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Channel ch SET ch.isBotEnabled = true WHERE ch.channelId = :channelId ")
    void changeIsBotEnabledToTrue(@Param("channelId") long channelId);

    /**
     * Returns a list of internal ids of Channels which had a match with channelIds list
     *
     * @param channelIds list of long identifiers of channels
     * @return A list of internal ids of channels
     */
    @Query("SELECT ch.id FROM Channel ch WHERE ch.channelId IN :channelIds")
    List<Integer> findAllIdsByChannelIdFromUniqueChannelIdsList(@Param("channelIds") List<Long> channelIds);
}
