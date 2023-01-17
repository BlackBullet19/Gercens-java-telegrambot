package org.telran.project.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telran.project.telegrambot.model.Channel;



@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {

    Channel findById(long id);
}
