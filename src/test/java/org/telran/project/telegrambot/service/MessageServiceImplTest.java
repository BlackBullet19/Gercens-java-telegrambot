package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telran.project.telegrambot.model.Message;
import org.telran.project.telegrambot.repository.MessageRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MessageServiceImplTest {

    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    private Message messageOne;
    private Message messageTwo;

    @BeforeEach
    void setUp() {
        messageService = new MessageServiceImpl(messageRepository);
        messageOne = new Message(10, "titleOne", 100, "textTwo");
        messageOne.setNew(true);
        messageOne.setId(1);
        messageTwo = new Message(20, "titleTwo", 200, "textTwo");
        messageTwo.setNew(false);
        messageRepository.save(messageOne);
        messageRepository.save(messageTwo);
    }

    @AfterEach
    void tearDown() {
        messageRepository.deleteAll();
    }

    @Test
    void canGetListOfMessage() {
        List<Message> list = messageService.list();
        assertEquals(2, list.size());
    }

    @Test
    void canGetMessageByMessageId() {
        Message message = messageService.getMessage(10);
        assertEquals(messageOne, message);
    }

    @Test
    void canCreateMessageWithConstructor() {
        messageService.createMessage(30, "titleThree", 100, "textThree");
        Message message = messageService.getMessage(30);
        assertEquals(100, message.getChatId());
    }

    @Test
    void canRemoveMessage() {
        messageService.removeMessage(10);
        List<Message> list = messageService.list();
        assertEquals(1, list.size());
    }

    @Test
    void canUpdateMessage() {
        Message message = messageService.updateMessage(10);
        assertEquals(messageOne, message);
    }

    @Test
    void canCreateMessageWithMessage() {
        Message message = new Message(30, "titleThree", 100, "textThree");
        messageService.createMessage(message);
        Message gotMessage = messageService.getMessage(30);
        assertEquals(100, gotMessage.getChatId());
    }

    @Test
    void canListAllNewMessages() {
        List<Message> newMessages = messageService.listAllNewMessages();
        assertEquals(messageOne, newMessages.get(0));
    }

    @Test

    void canChangeIsNewToFalse() {
        Message messageBefore = messageService.getMessage(10);
        int id = messageBefore.getId();
        messageService.changeIsNewToFalse(id, id);
        Message messageAfter = messageService.getMessage(10);
        assertFalse(messageAfter.isNew());

    }
}