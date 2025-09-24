package com.valo.journalApp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserEntryRepositoryImplTests {
    @Autowired
    private UserEntryRepositoryImpl userEntryRepositoryImpl;

    @Test
    public void test() {
        userEntryRepositoryImpl.getAllUserWithSA();
    }

}
