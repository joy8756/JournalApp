package com.valo.journalApp.service;

import com.valo.journalApp.entity.JournalEntity;
import com.valo.journalApp.entity.UserEntity;
import com.valo.journalApp.repository.UserEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserEntryService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(UserEntity obj){
        try {
            userEntryRepository.save(obj);
        } catch (Exception e){
            log.error("Exception :", e);
            throw new RuntimeException(e);
        }
    }

    public boolean saveNewEntry(UserEntity obj){
        try {
            obj.setPassword(passwordEncoder.encode(obj.getPassword()));
            userEntryRepository.save(obj);
            return true;
        } catch (Exception e){
            log.error("Exception :", e);
            return false;
        }
    }

    public List<UserEntity> getAll(){
        return userEntryRepository.findAll();
    }

    public UserEntity deleteEntry(String username){
        return userEntryRepository.deleteByUsername(username);
    }

    public UserEntity getByUsername(String username){
        return userEntryRepository.findByUsername(username);
    }
}
