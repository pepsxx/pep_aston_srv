package com.xandr.pep_aston.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BankAccountDoesNotBelongUserException extends RuntimeException {
    public BankAccountDoesNotBelongUserException(String message) {
        log.error(message);
    }
}
