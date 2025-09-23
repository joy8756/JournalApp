package com.valo.journalApp.service;

import com.valo.journalApp.entity.UserEntity;
import com.valo.journalApp.repository.UserEntryRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

//@SpringBootTest
//public class UserDetailsServiceImplTests {
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    // here already initialised due to @Autowired UserDetailsServiceImpl as spring context started due to @SpringBootTest
//    @MockitoBean
//    private UserEntryRepository userEntryRepository;
//
//    @Test
//    void loadUserByUsernameTests() {
//        when(userEntryRepository.findByUsername(ArgumentMatchers.anyString()))
//                .thenReturn(UserEntity.builder().username("Cbum").password("dfhdf567").roles(new ArrayList<>()).build());
//        UserDetails user = userDetailsService.loadUserByUsername("Ram");
//        Assertions.assertNotNull(user);
//    }
//}

// playing outside spring context
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserEntryRepository userEntryRepository;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        // to initialise userEntryRepository, or else it will be null
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void loadUserByUsernameTests() {
        when(userEntryRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(UserEntity.builder().username("Cbum").password("dfhdf567").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("Ram");
        Assertions.assertNotNull(user);
    }
}
