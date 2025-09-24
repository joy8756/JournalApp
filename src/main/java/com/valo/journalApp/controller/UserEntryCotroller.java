package com.valo.journalApp.controller;

import com.valo.journalApp.api.response.WeatherResponse;
import com.valo.journalApp.entity.UserEntity;
import com.valo.journalApp.service.UserEntryService;
import com.valo.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntryCotroller {
    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private WeatherService weatherService;

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userEntryService.deleteEntry(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateEntry(@RequestBody UserEntity obj){
        // SecurityContextHolder contains the authenticated data
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // this is username
        UserEntity old = userEntryService.getByUsername(username);
        old.setUsername(obj.getUsername());
        old.setPassword(obj.getPassword());
        userEntryService.saveEntry(old);
        return new ResponseEntity<>(old, HttpStatus.OK);
    }

    @GetMapping("/greeting")
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";
        if (weatherResponse != null) {
            int feelsLike = weatherResponse.getCurrent().getFeelslike();
            greeting = ", weather feels like " + feelsLike;
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    }
}
