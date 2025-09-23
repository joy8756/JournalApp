package com.valo.journalApp.service;

import com.valo.journalApp.entity.UserEntity;
import com.valo.journalApp.repository.UserEntryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@ActiveProfiles("dev")
public class UserEntityServiceTests {
    @Autowired
    private UserEntryRepository userEntryRepository;

    @Autowired
    private UserEntryService userEntryService;

    @Disabled
    @Test
    public void testAdd() {
        UserEntity user = userEntryRepository.findByUsername("Ram");
        assertTrue(user.getJournalEntities().isEmpty());
        assertNotNull(userEntryRepository.findByUsername("David"));
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = { // if integer then ints
            "Ram",
            "Shyam",
            "David"
    })
    public void test2(String name) {
        assertNotNull(userEntryRepository.findByUsername(name), "Found null for:" + name);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "4,2,6"
    })
    @Disabled
    public void test2(int a, int b, int expected) {
        assertEquals(expected, a+b);
    }

    @Disabled // for sonarCloud scan
    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void testSaveNEwUser(UserEntity user) {
        assertTrue(userEntryService.saveNewEntry(user));
    }
}
