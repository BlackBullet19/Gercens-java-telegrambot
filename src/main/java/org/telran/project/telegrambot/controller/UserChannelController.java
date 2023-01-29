package org.telran.project.telegrambot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telran.project.telegrambot.model.UserChannel;
import org.telran.project.telegrambot.service.UserChannelService;

import javax.validation.Valid;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/subscriptions/{userid}")
public class UserChannelController {

    @Autowired
    private UserChannelService userChannelService;

    @GetMapping
    public ResponseEntity<?> list(@PathVariable(name = "userid") int userId) {
        try {
            return new ResponseEntity<>(userChannelService.listUserChannels(userId), HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createUserSubscription(@PathVariable(name = "userid") int userId, @Valid @RequestBody UserChannel userChannel) {

        try {
            return new ResponseEntity<>(userChannelService.addUserSubscription(userId, userChannel), HttpStatus.OK);
        } catch (InvalidParameterException exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{channelid}")
    public ResponseEntity<?> deleteUserSubscription(@PathVariable(name = "userid") int userId,
                                                    @PathVariable(name = "channelid") int userChannel) {

        try {
            userChannelService.removeUserSubscription(userId, userChannel);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PutMapping("/activate")
    public ResponseEntity<?> activateSubscription(@PathVariable(name = "userid") int userId,
                                                  @Valid @RequestBody UserChannel userChannel) {
        try {
            return new ResponseEntity<>(userChannelService.activateSubscription(userId, userChannel), HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
        }
    }

    @PutMapping("/deactivate")
    public ResponseEntity<?> deactivateSubscription(@PathVariable(name = "userid") int userId,
                                                    @Valid @RequestBody UserChannel userChannel) {
        try {
            return new ResponseEntity<>(userChannelService.deactivateSubscription(userId, userChannel), HttpStatus.OK);
        } catch (InvalidParameterException exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
