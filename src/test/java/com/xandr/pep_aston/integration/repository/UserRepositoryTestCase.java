package com.xandr.pep_aston.integration.repository;

import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.integration.annotation.IT;
import com.xandr.pep_aston.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class UserRepositoryTestCase {

    private final UserRepository userRepository;

    private static final String NAME_OK = "Nik";
    private static final String NAME_BAD = "PepBadStringForTestName";
    private static final String PIN_OK = "wfMw0K/zHByHQD8eQ0e8whr/fBeZCHI1NfKzFyNwJSU=";
    private static final String PIN_BAD = "PepBadStringForTestPin";


    @Test
    void actualResultNotNullIfArgsIsNull() {

        String message = "Result should be Optional";
        assertNotNull(userRepository.findAllByNameAndPin(NAME_BAD, null), message);
        assertNotNull(userRepository.findAllByNameAndPin(null, PIN_BAD), message);
        assertNotNull(userRepository.findAllByNameAndPin(null, null), message);

    }

    @Test
    void actualResultIsEmptyIfNameOrPinIsBad() {

        String message = "The user must not exist if, ";
        assertTrue(userRepository.findAllByNameAndPin(NAME_OK, PIN_BAD).isEmpty(), message + "the pin is incorrect.");
        assertTrue(userRepository.findAllByNameAndPin(NAME_BAD, PIN_OK).isEmpty(), message + "the name is incorrect.");
        assertTrue(userRepository.findAllByNameAndPin(NAME_BAD, PIN_BAD).isEmpty(), message + "the name and pin is incorrect.");

    }

    @Test
    void userPresentIfNameOrPinIsOk() {

        String message = "The user must exist if, the name and pin is correct.";
        assertTrue(userRepository.findAllByNameAndPin(NAME_OK, PIN_OK).isPresent(), message);

    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    void actualResultEqualsExpectedResult() {

        Optional<User> maybeActualResult = userRepository.findAllByNameAndPin(NAME_OK, PIN_OK);

        assertTrue(maybeActualResult.isPresent());
        User actualResult = maybeActualResult.get();

        assertTrue(actualResult.getId() > 0, "User id should be positive");
        assertEquals(PIN_OK, actualResult.getPin(), "User pin should be mast same");
        assertEquals(NAME_OK, actualResult.getName(), "User name should be mast same");

    }
}