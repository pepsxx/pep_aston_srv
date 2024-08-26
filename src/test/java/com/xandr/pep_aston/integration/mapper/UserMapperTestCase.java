package com.xandr.pep_aston.integration.mapper;

import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.integration.annotation.IT;
import com.xandr.pep_aston.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@IT
@AllArgsConstructor
class UserMapperTestCase {

    private final UserMapper userMapper;

    private static final UserDto userDto = new UserDto();
    private static final String NAME = "Nik";
    private static final String PIN = "wfMw0K/zHByHQD8eQ0e8whr/fBeZCHI1NfKzFyNwJSU=";

    @Test
    void actualResultNullIfArgIsNull() {

        String message = "user should null after mapper if userDao is null";
        assertNull(userMapper.userDtoToUser(null), message);

    }

    @Test
    void actualResultPresentIfArgIsNotNull() {

        String message = "user should not be null after mapper if userDao is not null";
        assertNotNull(userMapper.userDtoToUser(userDto), message);

    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void actualResultEqualsExpectedResult() {

        userDto.setPin(PIN);
        userDto.setName(NAME);

        User actualResult = userMapper.userDtoToUser(userDto);

        assertEquals(actualResult.getName(), userDto.getName(), "username should be the same");
        assertEquals(actualResult.getPin(), userDto.getPin(), "user pin should be the same");

    }
}