package org.telran.project.telegrambot.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Message is main entity we'll use to work with message, id, title of group/channel
 * data bot has received
 *
 * @author Olegs Gercens
 * @version 1.0
 */
@Entity
@Table(name = "message")
public class Message {

    /**
     * Unique internal Message identifier we'll use everywhere concerning Message
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Channel name received from bot
     */
    @NotBlank
    @Column(name = "groupName")
    private String title;

    /**
     * Unique Channel identifier received from bot
     */
    @NotNull
    @Column(name = "groupId")
    private long chatId;

    /**
     * Message text received from bot
     */
    @Column(name = "message")
    private String text;

    /**
     * This value describes is message new or old, when creating event used messages marks as old(inNew=false) , by default
     * at message creation isNew=true
     */
    private boolean isNew;

    public Message(String title, long chatId, String text) {
        this.title = title;
        this.chatId = chatId;
        this.text = text;
        this.isNew = true;
    }

    public Message() {
        //
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
