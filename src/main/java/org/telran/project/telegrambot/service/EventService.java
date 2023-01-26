package org.telran.project.telegrambot.service;


import org.telran.project.telegrambot.model.Event;

import java.util.List;

public interface EventService {


    void createNewEvents();

    List<Event> getNewEventsByUserId(int userId);

    void createEvent(int userId, String text, int channelId);

}
