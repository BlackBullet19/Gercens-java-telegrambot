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

    /**
     * Return all messages which is new
     * @return List of new messages
     */
    @Query("SELECT m FROM Message m WHERE m.isNew = true")
    List<Message> findAllNewMessages();

    /**
     * Changing value of isNew to false depending on ids from Integer list
     * @param messageIds
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Message m SET m.isNew = false WHERE m.id IN :messageIds")
    void changeIsNewToFalse(@Param("messageIds") List<Integer> messageIds);
}
