package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.Event;

import java.util.List;

/**
 * EventService interface to implement to work with events for users
 *
 * @author Olegs Gercens
 */
public interface EventService {

    /**
     * Main method to implement for creating events based on data collected from all services
     */
    void createNewEvents();

    /**
     * Returns a list of new events for specific user
     *
     * @param userId specific user identifier
     * @return list of new events for specific user
     */
    List<Event> getNewEventsByUserId(int userId);

    /**
     * Returns a list of all events stored
     *
     * @return list of all events stored
     */
    List<Event> listAll();
}
