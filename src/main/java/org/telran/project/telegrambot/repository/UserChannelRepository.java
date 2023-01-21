package org.telran.project.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.telran.project.telegrambot.model.UserChannel;

import java.util.List;

@Repository
public interface UserChannelRepository extends JpaRepository<UserChannel, Integer> {

    @Query("SELECT uc FROM UserChannel uc WHERE uc.userId = :userId")
    List<UserChannel> findByUserId(@Param("userId") int userId);

    @Query("SELECT uc FROM UserChannel uc WHERE uc.userId = :userId AND uc.channelId = :channelId")
    UserChannel findByUserIdAndChannelId(@Param("userId") int userId, @Param("channelId") long channelId);


    boolean existsByUserId(int userId);

    @Query("SELECT uc.userId FROM UserChannel uc WHERE uc.channelId = :channelId")
    List<Integer> findAllUserIdByChannelId(@Param("channelId") long channelId);
}
