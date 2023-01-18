package org.telran.project.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telran.project.telegrambot.model.TelegramMessage;

@Repository
public interface TelegramMessageRepository extends JpaRepository<TelegramMessage, Integer> {

    TelegramMessage findByMessageId(int id);
}
