package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telran.project.telegrambot.model.Message;
import org.telran.project.telegrambot.repository.MessageRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class MessageServiceImplTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void setUp() {
        messageRepository.deleteAll();
    }

    @Test
    void createMessageWithAllNonEmptyFields() {
        List<Message> messageList = messageRepository.findAll();
        assertEquals(0, messageList.size());
        Message savingMessage = messageService.createMessage("title1", 123, "text");
        List<Message> messageListAfterSavingOneMessage = messageRepository.findAll();
        assertEquals(1, messageListAfterSavingOneMessage.size());
        Message message = messageRepository.findById(savingMessage.getId()).get();
        assertEquals(123L, message.getChatId());
    }

    @Test
    void createMessageWithWrongChatIdField() {
        List<Message> messageList = messageRepository.findAll();
        assertEquals(0, messageList.size());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                messageService.createMessage("title1", 0, "text"));
        assertEquals("At least one entered parameter was 0 or empty", exception.getMessage());
        List<Message> messageListAfterSavingOneMessage = messageRepository.findAll();
        assertEquals(0, messageListAfterSavingOneMessage.size());
    }

    @Test
    void createMessageWithWrongTitleField() {
        List<Message> messageList = messageRepository.findAll();
        assertEquals(0, messageList.size());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                messageService.createMessage(null, 123, "text"));
        assertEquals("At least one entered parameter was 0 or empty", exception.getMessage());
        List<Message> messageListAfterSavingOneMessage = messageRepository.findAll();
        assertEquals(0, messageListAfterSavingOneMessage.size());
    }

    @Test
    void getMessagesAndMarkThemOld() {
        List<Message> findAllFromRepository = messageRepository.findAll();
        assertEquals(0, findAllFromRepository.size());
        Message messageOne = new Message("title1", 123, "text");
        Message messageTwo = new Message("title1", 123, "text");
        Message messageThree = new Message("title1", 123, "text");
        messageRepository.save(messageOne);
        messageRepository.save(messageTwo);
        messageRepository.save(messageThree);
        List<Message> allAfterSave = messageRepository.findAll();
        Message message = allAfterSave.get(1);
        assertTrue(message.isNew());
        messageService.getMessagesAndMarkThemOld();
        List<Message> allMessagesIsNewFalse = messageRepository.findAll();
        Message messageIsNewFalse = allMessagesIsNewFalse.get(1);
        assertFalse(messageIsNewFalse.isNew());
    }
}