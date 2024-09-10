package com.xandr.pep_aston.service.impl;

import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.mapper.UserMapper;
import com.xandr.pep_aston.repository.UserRepository;
import com.xandr.pep_aston.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;
    public final UserMapper userMapper;

    @Override
    public Optional<User> findByNameAndPin(String name, String pin) {

        return userRepository.findAllByNameAndPin(name, pin);

    }

    @Override
    public Optional<UserDto> create(UserDto userDto) {

        boolean userPresent = Optional.of(userDto)
                .map(userMapper::userDtoToUser)
                .flatMap(u -> userRepository.findFirstByName(u.getName()))
                .isPresent();

        if (userPresent) {
            log.warn("Пользователь уже существует");
            return Optional.empty();
        }

        return Optional.of(User.builder()
                        .name(userDto.getName())
                        .pin(userDto.getPin())
                        .build())
                .map(userRepository::save)
                .map(userMapper::userToUserDto);

    }
}
