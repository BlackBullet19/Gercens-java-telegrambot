package org.telran.project.telegrambot.model;

import javax.persistence.*;

@Entity
@Table(name = "channel")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private long channelId;

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long groupId) {
        this.channelId = groupId;
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
}
