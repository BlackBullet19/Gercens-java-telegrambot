package org.telran.project.telegrambot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telran.project.telegrambot.model.UserSchedule;
import org.telran.project.telegrambot.service.UserScheduleService;

@RestController
@RequestMapping("/userschedule")
public class UserScheduleController {

    @Autowired
    private UserScheduleService userScheduleService;

    public void createUserSchedule(int userId, UserSchedule schedule) {
        userScheduleService.createUserSchedule(userId, schedule);
    }

    public UserSchedule updateUserSchedule(int userId) {
        return userScheduleService.updateUserSchedule(userId);
    }

    public void removeUserSchedule(int userId) {
        userScheduleService.removeUserSchedule(userId);
    }

    public UserSchedule getUserSchedule(int userId) {
        return userScheduleService.getUserSchedule(userId);
    }
}
