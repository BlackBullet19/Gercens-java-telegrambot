package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.Statistic;

import java.util.List;

/**
 * StatisticService interface to implement for generating statistic about application
 *
 * @author Olegs Gercens
 */
public interface StatisticService {

    /**
     * Returns a list of all Statistic entities
     *
     * @param userId - internal user identifier
     * @return a list of all Statistic entities
     */
    List<Statistic> getAllStatistic(int userId);

    /**
     * Registers to save new Statistic entity
     *
     * @param userId - internal user identifier
     * @return Statistic entity
     */
    Statistic createNewStatistic(int userId);
}
