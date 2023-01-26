package org.telran.project.telegrambot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.service.ChannelService;

import javax.validation.Valid;
import java.security.InvalidParameterException;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getChannel(@PathVariable(name = "id") int id) {
        try {
            return new ResponseEntity<>(channelService.getChannel(id), HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping()
    public ResponseEntity<?> createChannel(@Valid @RequestBody Channel channel) {
        try {
            return new ResponseEntity<>(channelService.createChannel(channel), HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (IllegalStateException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChannel(@PathVariable(name = "id") int id) {
        try {
            channelService.deleteChannel(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChannel(@PathVariable(name = "id") int id,@Valid @RequestBody Channel channel) {
        try {
            return new ResponseEntity<>(channelService.updateChannel(id, channel), HttpStatus.OK);
        } catch (InvalidParameterException exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @GetMapping
    public ResponseEntity<?> listChannels() {
        try {
            return new ResponseEntity<>(channelService.listChannels(), HttpStatus.OK);
        } catch (InvalidParameterException exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
