package org.telran.project.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.telran.project.telegrambot.model.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Message findByMessageId(int id);

    @Query("SELECT m FROM Message m WHERE m.isNew = true")
    List<Message> findAllNewMessages();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Message m SET m.isNew = false WHERE m.id BETWEEN :fromId AND :toId")
    void changeIsNewToFalse(@Param("fromId") int fromId, @Param("toId") int toId );
}
