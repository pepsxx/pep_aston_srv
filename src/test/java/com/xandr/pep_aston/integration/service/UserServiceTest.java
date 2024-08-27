package com.xandr.pep_aston.integration.service;

import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.integration.IntegrationTestBase;
import com.xandr.pep_aston.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserServiceTest extends IntegrationTestBase {

    private final UserService userService;

    private static final String NAME_OK = "Nik";
    private static final String NAME_BAD = "PepBadStringForTestName";
    private static final String PIN_OK = "wfMw0K/zHByHQD8eQ0e8whr/fBeZCHI1NfKzFyNwJSU=";
    private static final String PIN_BAD = "PepBadStringForTestPin";

    @Test
    @DisplayName("Фактический результат NotNull, если аргументы Null")
    void actualResultNotNullIfArgsIsNull() {

        String message = "Result should be Optional";
        assertNotNull(userService.findByNameAndPin(null, null), message);
        assertNotNull(userService.findByNameAndPin(NAME_BAD, null), message);
        assertNotNull(userService.findByNameAndPin(null, PIN_BAD), message);

    }

    @Test
    @DisplayName("Фактический результат пуст, если имя или пин-код неверны")
    void actualResultIsEmptyIfNameOrPinIsBad() {

        String message = "The user must not exist if, ";
        assertTrue(userService.findByNameAndPin(NAME_OK, PIN_BAD).isEmpty(), message + "the pin is incorrect.");
        assertTrue(userService.findByNameAndPin(NAME_BAD, PIN_OK).isEmpty(), message + "the name is incorrect.");
        assertTrue(userService.findByNameAndPin(NAME_BAD, PIN_BAD).isEmpty(), message + "the name and pin is incorrect.");

    }

    @Test
    @DisplayName("Пользователь присутствует, если имя и пин-код верны")
    void userPresentIfNameAndPinOk() {

        String message = "The user must exist if, the name and pin is correct.";
        assertTrue(userService.findByNameAndPin(NAME_OK, PIN_OK).isPresent(), message);

    }

    @Test
    @DisplayName("Фактический результат равен ожидаемому результату")
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    void actualResultEqualsExpectedResult() {

        Optional<User> maybeActualResult = userService.findByNameAndPin(NAME_OK, PIN_OK);

        assertTrue(maybeActualResult.isPresent());
        User actualResult = maybeActualResult.get();

        User exceptedResult = User.builder()
                .id(actualResult.getId())
                .name(NAME_OK)
                .pin(PIN_OK)
                .build();

        assertEquals(exceptedResult, actualResult, "User should be equals to ExpectedUser");
        assertEquals(exceptedResult.getName(), actualResult.getName(), "User name should be mast same");
        assertEquals(exceptedResult.getPin(), actualResult.getPin(), "User pin should be mast same");
        assertEquals(exceptedResult.getId(), actualResult.getId(), "User id should be mast same");

    }
}

