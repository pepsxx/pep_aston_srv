package com.xandr.pep_aston.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException(String message) {
        log.error(message);
    }
}
