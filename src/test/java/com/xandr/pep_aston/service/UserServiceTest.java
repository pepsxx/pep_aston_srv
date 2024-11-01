package com.xandr.pep_aston.service;

import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.repository.UserRepository;
import com.xandr.pep_aston.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private static UserRepository userRepository;

    @InjectMocks
    private static UserServiceImpl userService;

    private static final String NAME = "PepBadStringForTestName";
    private static final String PIN = "PepBadStringForTestPin";
    private static final Long ID = 1L;

    @Test
    @DisplayName("Поиск по имени и пин-коду")
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void findByNameAndPin() {

        User user = User.builder()
                .id(ID)
                .name(NAME)
                .pin(PIN)
                .build();
        
        doReturn(Optional.of(user)).when(userRepository).findAllByNameAndPin(NAME, PIN);
        
        Optional<User> maybeActualResult = userService.findByNameAndPin(NAME, PIN);
        
        assertTrue(maybeActualResult.isPresent());


        User exceptedResult = User.builder()
                .id(ID)
                .name(NAME)
                .pin(PIN)
                .build();
        
        assertEquals(exceptedResult, maybeActualResult.get());

    }
}