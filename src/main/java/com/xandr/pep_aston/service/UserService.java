package com.xandr.pep_aston.service;

import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    public final UserRepository userRepository;

    public Optional<UserDto> findById(Integer id) {
        Optional<User> maybeUser = userRepository.findById(id);
        return maybeUser.stream()
                .map(u -> UserDto.builder()
                        .name(u.getName())
                        .pin(u.getPin())
                        .build())
                .findAny();
    }

    public List<UserDto> findAll() {
        List<User> allUser = userRepository.findAll();
        return allUser.stream()
                .map(u -> UserDto.builder()
                        .name(u.getName())
                        .pin(u.getPin())
                        .build())
                .toList();
    }

    public Integer findByNameAndPin(String name, Integer pin) {
        Optional<User> maybeUser = userRepository.findAllByNameAndPin(name, pin);
        return maybeUser.isPresent()
                ? maybeUser.get().getId()
                : 0;
    }
}
