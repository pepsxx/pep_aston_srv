package com.xandr.pep_aston.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BankAccountNotCorrectMoney extends RuntimeException {
    public BankAccountNotCorrectMoney(String message) {
        log.error(message);
    }
}
