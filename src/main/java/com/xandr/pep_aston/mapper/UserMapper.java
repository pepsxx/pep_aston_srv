package com.xandr.pep_aston.mapper;

import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bankAccounts", ignore = true)
    User userDtoToUser(UserDto userDto);
}
