package org.telran.project.telegrambot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telran.project.telegrambot.model.UserChannel;
import org.telran.project.telegrambot.service.UserChannelService;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/userchannels/{userid}")
public class UserChannelController {

    @Autowired
    private UserChannelService userChannelService;

    @GetMapping
    public ResponseEntity<?> list(@PathVariable(name = "userid") int userId) {
        try {
            return new ResponseEntity<>(userChannelService.listUserChannels(userId), HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/{channelid}")
    public ResponseEntity<?> addUserSubscription(@PathVariable(name = "userid") int userId,
                                                 @Valid @PathVariable(name = "channelid") UserChannel userChannel) {

        try {
            return new ResponseEntity<>(userChannelService.addUserSubscription(userId, userChannel), HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{channelid}")
    public ResponseEntity<?> removeUserSubscription (@PathVariable(name = "userid") int userId,
                                                     @PathVariable(name = "channelid") int userChannel) {

        try {
            userChannelService.removeUserSubscription(userId, userChannel);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
