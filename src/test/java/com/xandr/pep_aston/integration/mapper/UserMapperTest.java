package com.xandr.pep_aston.integration.mapper;

import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.integration.IntegrationTestBase;
import com.xandr.pep_aston.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserMapperTest extends IntegrationTestBase {

    private final UserMapper userMapper;

    private static final UserDto userDto = new UserDto();
    private static final String NAME = "Nik";
    private static final String PIN = "wfMw0K/zHByHQD8eQ0e8whr/fBeZCHI1NfKzFyNwJSU=";

    @Test
    @DisplayName("Фактический результат Null, если аргумент Null")
    void actualResultNullIfArgIsNull() {

        String message = "user should null after mapper if userDao is null";
        assertNull(userMapper.userDtoToUser(null), message);

    }

    @Test
    @DisplayName("Фактический результат присутствует, если аргумент не NotNull")
    void actualResultPresentIfArgIsNotNull() {

        String message = "user should not be null after mapper if userDao is not null";
        assertNotNull(userMapper.userDtoToUser(userDto), message);

    }

    @Test
    @DisplayName("Фактический результат равен ожидаемому результату")
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void actualResultEqualsExpectedResult() {

        userDto.setPin(PIN);
        userDto.setName(NAME);

        User actualResult = userMapper.userDtoToUser(userDto);

        assertEquals(actualResult.getName(), userDto.getName(), "username should be the same");
        assertEquals(actualResult.getPin(), userDto.getPin(), "user pin should be the same");

    }
}