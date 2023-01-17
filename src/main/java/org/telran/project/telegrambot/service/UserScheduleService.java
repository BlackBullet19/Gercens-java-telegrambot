package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.UserSchedule;

public interface UserScheduleService {

    void createUserSchedule(int userId, UserSchedule schedule);

    UserSchedule updateUserSchedule(int userId);

    void removeUserSchedule(int userId);

    UserSchedule getUserSchedule(int userId);
}
