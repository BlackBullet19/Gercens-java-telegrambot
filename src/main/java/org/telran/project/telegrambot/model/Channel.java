package org.telran.project.telegrambot.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "channel")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    @NotNull
    private long channelId;

    private boolean isBotEnabled = true;

    public boolean isBotEnabled() {
        return isBotEnabled;
    }

    public void setBotEnabled(boolean botEnabled) {
        isBotEnabled = botEnabled;
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

    public Channel(String name, long channelId) {
        this.name = name;
        this.channelId = channelId;
    }

    public Channel() {
    }
}
