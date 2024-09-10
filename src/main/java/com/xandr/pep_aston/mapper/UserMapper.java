package com.xandr.pep_aston.mapper;

import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bankAccounts", ignore = true)
    User userDtoToUser(UserDto userDto);

    @Mapping(target = "pin", ignore = true)
    UserDto userToUserDto(User user);
}
