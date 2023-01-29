package org.telran.project.telegrambot.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Event is entity we'll use for create notifications for users
 *
 * @author Olegs Gercens
 * @version 1.0
 */
@Entity
@Table(name = "event")
public class Event {

    /**
     * Unique internal Event identifier we'll use everywhere concerning Event
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Notification text
     */
    private String text;

    /**
     * Our users internal id
     */
    @NotNull
    @Digits(integer = Integer.MAX_VALUE, fraction = 0)
    private int userId;

    /**
     * Our channels internal id
     */
    @NotBlank
    private int channelId;

    /**
     * This value describes is event new or old, when user received notifications events mark old, by default
     * at creation isNew=true
     */
    private boolean isNew;

    public Event(String text, int userId, int channelId) {
        this.text = text;
        this.userId = userId;
        this.channelId = channelId;
        this.isNew = true;
    }

    public Event() {
        //
    }

    public boolean isNew() {
        return isNew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean getNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }
}
