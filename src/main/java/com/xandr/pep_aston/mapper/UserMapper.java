package com.xandr.pep_aston.mapper;

import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.User;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class UserMapper {

    public static Optional<User> mapToUser(UserDto userDto) {
        return Optional.ofNullable(User.builder()
                .name(userDto.getName())
                .pin(userDto.getPin())
                .build());
    }
}
