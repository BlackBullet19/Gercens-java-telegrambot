package org.telran.project.telegrambot.model;

import javax.persistence.*;

@Entity
@Table(name = "user_channels")
public class UserChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "channel_id")
    private long channelId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public UserChannel(int userId, long channelId) {
        this.userId = userId;
        this.channelId = channelId;
    }

    public UserChannel() {
    }
}
