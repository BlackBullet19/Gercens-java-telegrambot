package org.telran.project.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telran.project.telegrambot.configuration.BotConfiguration;

@SpringBootApplication
@Import(BotConfiguration.class)
@EnableScheduling
public class App {

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }
}
