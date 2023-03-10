package org.telran.project.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.telran.project.telegrambot.model.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    /**
     * Return a list of new events by given user id
     * @param userId must not be null
     * @return all new events for user
     */
    @Query("SELECT e FROM Event e WHERE e.isNew = true AND e.userId =:userId")
    List<Event> findAllByUserId(@Param("userId")int userId);
}
