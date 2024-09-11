package com.xandr.pep_aston.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestBodyValidationException extends Throwable {
    public RequestBodyValidationException(String message) {
        log.error(message);
    }
}
