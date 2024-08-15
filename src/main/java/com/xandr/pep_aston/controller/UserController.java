package com.xandr.pep_aston.controller;

import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // Тесты - Начало --------------------------------------------------------------------------------------------------
    @GetMapping("/test/1")
    public Optional<UserDto> test1() {
        Optional<UserDto> maybeUserDto = userService.findById(1);
        log.info("Результат test1 {}", maybeUserDto);
        return maybeUserDto;
    }

    @PostMapping("/test/1")
    public Optional<UserDto> test1p(@RequestBody User user) {
        Optional<UserDto> maybeUserDto = userService.findById(user.getId());
        log.info("Результат test1p {}", maybeUserDto);
        return maybeUserDto;
    }

    @GetMapping("/test/2")
    public List<UserDto> test2() {
        List<UserDto> userDtos = userService.findAll();
        log.info("Результат test2 {}", userDtos);
        return userDtos;
    }

    @PostMapping("/test/2")
    public Integer test2p(@RequestBody User user) {
        Integer maybeId = userService.findByNameAndPin(user.getName(), user.getPin());
        log.info("Результат test2p {}", maybeId);
        return maybeId;
    }
    // Тесты - Конец ---------------------------------------------------------------------------------------------------
}