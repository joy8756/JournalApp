package com.valo.journalApp.repository;

import com.valo.journalApp.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<UserEntity, ObjectId> {
    UserEntity findByUsername(String username);
    UserEntity deleteByUsername(String username);
}
