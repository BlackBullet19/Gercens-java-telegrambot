package org.telran.project.telegrambot.commands;

/**
 * Command enum used to recognize commands bot received to switch on/off saving messages for this
 * {@link org.telran.project.telegrambot.model.Channel}
 *
 * @author Olegs Gercens
 * @version 1.0
 */
public enum Command {

    START("/start"),
    STOP("/stop");

    private String name;

    public String getName() {
        return name;
    }

    Command(String name) {
        this.name = name;
    }
}
