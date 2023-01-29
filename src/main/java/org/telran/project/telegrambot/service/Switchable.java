package org.telran.project.telegrambot.service;

/**
 * Switchable interface to implement to add switchable options,
 * used to add on/off options for Channel
 *
 * @author Olegs Gercens
 */
public interface Switchable {

    /**
     * Implementation of this method adds option to turn Channel on for message creating
     *
     * @param channelId external channel/group identifier
     */
    void on(long channelId);

    /**
     * Implementation of this method adds option to turn Channel off for message creating
     *
     * @param channelId external channel/group identifier
     */
    void off(long channelId);
}
