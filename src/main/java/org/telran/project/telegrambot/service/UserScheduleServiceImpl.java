package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.UserSchedule;
import org.telran.project.telegrambot.repository.UserScheduleRepository;

@Service
public class UserScheduleServiceImpl implements UserScheduleService {


    private UserScheduleRepository userScheduleRepository;

    @Override
    public void createUserSchedule(int userId, UserSchedule schedule) {

    }

    @Override
    public UserSchedule updateUserSchedule(int userId) {
        return null;
    }

    @Override
    public void removeUserSchedule(int userId) {

    }

    @Override
    public UserSchedule getUserSchedule(int userId) {
        return null;
    }
}
