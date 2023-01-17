package org.telran.project.telegrambot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.model.User;
import org.telran.project.telegrambot.model.UserChannel;
import org.telran.project.telegrambot.service.UserChannelService;

import java.util.List;

@RestController
@RequestMapping("/userchannels/{userid}")
public class UserChannelController {

    @Autowired
    private UserChannelService userChannelService;

    @GetMapping
    public List<UserChannel> list(@PathVariable(name = "userid") int userId) {
        return userChannelService.listUserChannels(userId);
    }

    @PostMapping()
    public void addUserSubscription(@PathVariable(name = "userid") int userId, @RequestBody Channel channel) {
        userChannelService.addUserSubscription(userId, channel);
    }

    @DeleteMapping()
    public void removeUserSubscription(@PathVariable(name = "userid") int userId, @RequestBody Channel channel) {
        userChannelService.removeUserSubscription(userId, channel);
    }
}
