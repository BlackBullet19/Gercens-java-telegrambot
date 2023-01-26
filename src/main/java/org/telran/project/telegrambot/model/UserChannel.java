package org.telran.project.telegrambot.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "user_channel")
public class UserChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "user_id")
    private int userId;

    @NotNull
    @Column(name = "channel_id")
    private int channelId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserChannel(int userId, int channelId) {
        this.userId = userId;
        this.channelId = channelId;
    }

    public UserChannel() {
    }
}
