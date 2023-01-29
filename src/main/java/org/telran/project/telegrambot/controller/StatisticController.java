package org.telran.project.telegrambot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telran.project.telegrambot.service.StatisticService;

import javax.ws.rs.NotAcceptableException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private StatisticService service;

    @GetMapping("/{userid}")
    public ResponseEntity<?> statisticList(@PathVariable(name = "userid") int userId) {
        try {
            return new ResponseEntity<>(service.getAllStatistic(userId), HttpStatus.OK);
        } catch (NotAcceptableException exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping("/{userid}/new")
    public ResponseEntity<?> createNewStatistic(@PathVariable(name = "userid") int userId) {
        try {
            return new ResponseEntity<>(service.createNewStatistic(userId), HttpStatus.OK);
        } catch (NotAcceptableException exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
