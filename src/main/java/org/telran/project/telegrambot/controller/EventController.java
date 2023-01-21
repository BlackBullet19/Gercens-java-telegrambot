package org.telran.project.telegrambot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telran.project.telegrambot.model.Event;
import org.telran.project.telegrambot.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/events/{userid}")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> list(@PathVariable(name = "userid") int userId) {
        return eventService.getNewEventsByUserId(userId);
    }
}
