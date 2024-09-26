package com.xandr.pep_aston.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        log.error(message);
    }
}
