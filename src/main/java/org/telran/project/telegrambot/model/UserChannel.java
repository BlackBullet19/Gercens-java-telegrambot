package org.telran.project.telegrambot.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * UserChannel entity is subscription, we'll be using to link users to channels
 * and based on this data - create events for users
 *
 * @author Olegs Gercens
 * @version 1.0
 */
@Entity
@Table(name = "user_channel")
public class UserChannel {

    /**
     * Unique internal subscription identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Unique internal user identifier
     */
    @NotNull
    @Column(name = "user_id")
    private int userId;

    /**
     * Unique internal channel identifier
     */
    @NotNull
    @Column(name = "channel_id")
    private int channelId;

    /**
     * It's value describes is subscription active or not, by default, when created it's active(true)
     */
    @Column(name = "is_subscribed")
    private boolean isSubscribed;

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

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public UserChannel(int userId, int channelId) {
        this.userId = userId;
        this.channelId = channelId;
        this.isSubscribed = true;
    }

    public UserChannel() {
        //
    }
}
