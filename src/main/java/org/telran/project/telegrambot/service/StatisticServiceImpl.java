package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.Event;
import org.telran.project.telegrambot.model.Statistic;
import org.telran.project.telegrambot.model.UserRole;
import org.telran.project.telegrambot.repository.StatisticRepository;
import javax.ws.rs.NotAcceptableException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * StatisticService implementation for generating statistic about how many events were sent to users,
 * only users with status ADMIN have access
 *
 * @author Olegs Gercens
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    /**
     * Returns a list of all Statistic entities
     *
     * @param userId - internal user identifier, user must have status ADMIN for access
     * @return a list of all Statistic entities
     * @throws IllegalArgumentException - if user identifier was 0
     * @throws NoSuchElementException   - if user not found
     * @throws NotAcceptableException   - if user have no access
     */
    @Override
    public List<Statistic> getAllStatistic(int userId) {
        if (userId == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (!userService.existsById(userId)) {
            throw new NoSuchElementException("User with ID " + userId + " not found");
        }
        if (!checkTolerance(userId)) {
            throw new NotAcceptableException("User with ID " + userId + " have no access");
        }
        return statisticRepository.findAll();
    }

    /**
     * Registers to save new Statistic entity
     *
     * @param userId - internal user identifier, user must have status ADMIN for access
     * @return Statistic entity
     * @throws IllegalArgumentException - if user identifier was 0
     * @throws NoSuchElementException   - if user not found
     * @throws NotAcceptableException   - if user have no access
     */
    @Override
    public Statistic createNewStatistic(int userId) {
        if (userId == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (userService.existsById(userId)) {
            if (checkTolerance(userId)) {
                int newLastEventIndex;
                int oldLastEventIndex;
                if (eventService.listAll().isEmpty()) {
                    newLastEventIndex = 0;
                } else
                    newLastEventIndex = eventService.listAll().stream().map(Event::getId).max(Integer::compare).get();
                if (statisticRepository.findAll().isEmpty()) {
                    oldLastEventIndex = 0;
                } else oldLastEventIndex = statisticRepository.findAll()
                        .stream().map(Statistic::getLastIndexOfLastEvent).max(Integer::compare).get();
                int newEventCount = newLastEventIndex - oldLastEventIndex;
                String statisticMessage = "From last time bot sent " + newEventCount + " new events!";
                return statisticRepository.save(new Statistic(statisticMessage, newLastEventIndex));
            }
            throw new NotAcceptableException("User with ID " + userId + " have no access");
        }
        throw new NoSuchElementException("User with ID " + userId + " not found");
    }

    /**
     * This method checks if user have access to statistics, user must have status ADMIN
     *
     * @param userId internal user identifier, user must have status ADMIN for access
     * @return true if user have access
     * @throws IllegalArgumentException - if user identifier was 0
     */
    private boolean checkTolerance(int userId) {
        if (userId == 0) throw new IllegalArgumentException("Entered value can not be 0");
        return UserRole.ADMIN.equals(userService.getUser(userId).getStatus());
    }
}
