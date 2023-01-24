package org.telran.project.telegrambot.model;



import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int messageId;

    @Column(name = "groupName")
    private String title;

    @Column(name = "groupId")
    private long chatId;

    @Column(name = "message")
    private String text;

    private boolean isNew = true;

    public Message(int messageId, String title, long chatId, String text) {
        this.messageId = messageId;
        this.title = title;
        this.chatId = chatId;
        this.text = text;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return messageId == message.messageId && chatId == message.chatId && isNew == message.isNew && Objects.equals(title, message.title) && Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, title, chatId, text, isNew);
    }
}
