package org.telran.project.telegrambot.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Channel is main entity which contains data received from bot about channel to use here,
 * we'll be using to get names, ids of groups,supergroups, channels and use it for subscriptions
 *
 * @author Olegs Gercens
 * @version 1.0
 */
@Entity
@Table(name = "channel")
public class Channel {

    /**
     * Unique internal Channel identifier we'll use everywhere concerning Channel
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Channel name received from bot
     */
    @NotBlank
    private String name;

    /**
     * Unique Channel identifier received from bot
     */
    @NotNull
    private long channelId;

    /**
     * Parameter describing is this specific channel enabled for bot activity,
     * by default is true(active)
     */
    private boolean isBotEnabled;

    public boolean isBotEnabled() {
        return isBotEnabled;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBotEnabled(boolean botEnabled) {
        isBotEnabled = botEnabled;
    }

    public Channel(String name, long channelId) {
        this.name = name;
        this.channelId = channelId;
        this.isBotEnabled = true;
    }

    public Channel() {
        //
    }
}
