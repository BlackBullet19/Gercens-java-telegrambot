package org.telran.project.telegrambot.commands;

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
