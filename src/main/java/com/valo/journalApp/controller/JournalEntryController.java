package com.valo.journalApp.controller;

import com.valo.journalApp.entity.JournalEntity;
import com.valo.journalApp.entity.UserEntity;
import com.valo.journalApp.service.JournalEntryService;
import com.valo.journalApp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalService;
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping
    public ResponseEntity<?> getUserJournalAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity currUser= userEntryService.getByUsername(username);
        if(currUser!=null) {
            List<JournalEntity> all = currUser.getJournalEntities();
            if (all!=null && !all.isEmpty()) {
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getAll(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userEntryService.getByUsername(username);
        List<JournalEntity> collect = user.getJournalEntities().stream()
                .filter(x -> x.getId().equals(myId))
                .toList();
        if (!collect.isEmpty()){
            // means myId is present in the authenticated user
            Optional<JournalEntity> obj = journalService.getById(myId);
            if (obj.isPresent()) {
                return new ResponseEntity<>(obj.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntity obj){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalService.saveEntry(obj, username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntity obj){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userEntryService.getByUsername(username);
        List<JournalEntity> collect = user.getJournalEntities().stream()
                .filter(x -> x.getId().equals(myId))
                .toList();
        if (!collect.isEmpty()){
            JournalEntity old = journalService.getById(myId).orElse(null);
            if(old!=null) {
                old.setContent(obj.getContent());
                old.setTitle(obj.getTitle());
                journalService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userEntryService.getByUsername(username);
        List<JournalEntity> collect = user.getJournalEntities().stream()
                .filter(x -> x.getId().equals(myId))
                .toList();
        boolean removed = false;
        if (!collect.isEmpty()){
            removed = journalService.deleteEntry(myId, username);
        }
        if (removed) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
