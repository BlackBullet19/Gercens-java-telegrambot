package org.telran.project.telegrambot.model;

import javax.persistence.*;

/**
 * Statistic entity we'll use to make statistics about this application
 *
 * @author Olegs Gercens
 * @version 1.0
 */
@Entity
@Table(name = "statistic")
public class Statistic {

    /**
     * Unique internal Statistic identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Text containing specific information about statistic
     */
    private String statisticMessage;

    /**
     * Used to store last internal id of event to create new statistic based on old
     */
    private int lastIndexOfLastEvent;

    public Statistic() {
        //
    }

    public Statistic(String statisticMessage, int lastIndexOfLastEvent) {
        this.statisticMessage = statisticMessage;
        this.lastIndexOfLastEvent = lastIndexOfLastEvent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLastIndexOfLastEvent() {
        return lastIndexOfLastEvent;
    }

    public String getStatisticMessage() {
        return statisticMessage;
    }

    public void setStatisticMessage(String statisticMessage) {
        this.statisticMessage = statisticMessage;
    }
}
