package com.valo.journalApp.controller;

import com.valo.journalApp.entity.UserEntity;
import com.valo.journalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserEntryService userEntryService;

    @PostMapping
    public void createUser(@RequestBody UserEntity obj){
        userEntryService.saveNewEntry(obj);
    }
}
