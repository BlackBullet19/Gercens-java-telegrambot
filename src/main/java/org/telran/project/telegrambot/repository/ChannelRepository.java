package org.telran.project.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.telran.project.telegrambot.model.Channel;



@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {

    Channel findByChannelId(long id);

    boolean existsByChannelId(long id);

    @Transactional
    @Modifying
    @Query("UPDATE Channel ch SET ch.isBotEnabled = false WHERE ch.channelId = :channelId ")
    void changeIsBotEnabledToFalse(@Param("channelId") long channelId);

    @Transactional
    @Modifying
    @Query("UPDATE Channel ch SET ch.isBotEnabled = true WHERE ch.channelId = :channelId ")
    void changeIsBotEnabledToTrue(@Param("channelId") long channelId);
}
