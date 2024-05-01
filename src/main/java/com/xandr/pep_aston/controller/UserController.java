package com.xandr.pep_aston.controller;

import com.xandr.pep_aston.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping
    public ResponseEntity<UserDto> grtUser() {
        UserDto userDto = UserDto.builder()
                .name("Tom")
                .age(18)
                .build();
        log.info("Был передан пользователь {}", userDto);
        return ResponseEntity.ok(userDto);
    }
}
