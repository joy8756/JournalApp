package com.valo.journalApp.repository;

import com.valo.journalApp.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserEntryRepositoryImpl {
    @Autowired
    private MongoTemplate mongoTemplate;

    // criteria API, custom and more complex than query method dsl
    public List<UserEntity> getAllUserWithUsername() {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is("Ram"));
        query.addCriteria(Criteria.where("sentimentAnalysis").ne(false));
        List<UserEntity> users = mongoTemplate.find(query, UserEntity.class);
        return users;
    }

    public List<UserEntity> getAllUserWithSA() {
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(criteria.andOperator(
                Criteria.where("email").exists(true),
                Criteria.where("email").ne(null).ne(""),
                Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"),
                Criteria.where("sentimentAnalysis").is(true))
        );
        List<UserEntity> users = mongoTemplate.find(query, UserEntity.class);
        return users;
    }
}
