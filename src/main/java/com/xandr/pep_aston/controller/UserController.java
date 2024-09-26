package com.xandr.pep_aston.controller;

import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.exception.RequestBodyValidationException;
import com.xandr.pep_aston.service.UserService;
import com.xandr.pep_aston.util.HashCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @SneakyThrows
    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new RequestBodyValidationException("Validation RequestBody Exception for UserDto");
        }

        userDto.setPin(HashCodeUtil.getSHA256Hash(userDto.getPin()));

        return userService.create(userDto)
                .map(u -> ResponseEntity.status(HttpStatus.CREATED).body(u))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).body(null));

    }
}