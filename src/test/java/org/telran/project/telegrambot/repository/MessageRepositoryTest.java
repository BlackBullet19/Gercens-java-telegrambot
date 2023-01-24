package org.telran.project.telegrambot.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telran.project.telegrambot.model.Message;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    private Message messageOne;
    private Message messageTwo;

    @BeforeEach
    void setUp() {
        messageOne = new Message(1, "titleOne", 100, "textOne");
        messageOne.setNew(true);
        messageTwo = new Message(2, "titleTwo", 200, "textTwo");
        messageTwo.setNew(false);
        messageRepository.save(messageOne);
        messageRepository.save(messageTwo);
    }

    @AfterEach
    void tearDown() {
        messageRepository.deleteAll();
    }

    @Test
    void canFindByMessageId() {
        Message byMessageId = messageRepository.findByMessageId(1);
        assertEquals("titleOne", byMessageId.getTitle());
    }

    @Test
    void canFindAllNewMessages() {
        List<Message> allNewMessages = messageRepository.findAllNewMessages();
        assertEquals(1,allNewMessages.size());
    }

    @Test
    void canChangeIsNewToFalse() {
        Message byMessageId = messageRepository.findByMessageId(1);
        int id = byMessageId.getId();
        messageRepository.changeIsNewToFalse(id, id);
        Message byMessageIdAfter = messageRepository.findByMessageId(1);
        assertFalse(byMessageIdAfter.isNew());
    }
}