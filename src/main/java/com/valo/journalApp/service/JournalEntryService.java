package com.valo.journalApp.service;

import com.valo.journalApp.entity.JournalEntity;
import com.valo.journalApp.entity.UserEntity;
import com.valo.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserEntryService userEntryService;

    @Transactional
    public void saveEntry(JournalEntity obj, String username){
        try {
            UserEntity user = userEntryService.getByUsername(username);
            obj.setDate(LocalDate.now());
            JournalEntity journal = journalEntryRepository.save(obj);
            user.getJournalEntities().add(journal);
            userEntryService.saveEntry(user);
        } catch (Exception e){
            log.error("Exception :", e);
            throw new RuntimeException("Exception", e);
        }
    }

    public void saveEntry(JournalEntity obj){
        try {
            obj.setDate(LocalDate.now());
            JournalEntity journal = journalEntryRepository.save(obj);
        } catch (Exception e){
            log.error("Exception :", e);
        }
    }

    public List<JournalEntity> getAll(){
        return journalEntryRepository.findAll();
    }

    public boolean deleteEntry(ObjectId id, String username){
        boolean removed = false;
        try {
            UserEntity user = userEntryService.getByUsername(username);
            removed = user.getJournalEntities().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userEntryService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Some error occurred while deleting.", e );
        }
        return removed;
    }

    public Optional<JournalEntity> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

}
