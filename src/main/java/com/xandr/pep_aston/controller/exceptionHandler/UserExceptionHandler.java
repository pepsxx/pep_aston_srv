package com.xandr.pep_aston.controller.exceptionHandler;

import com.xandr.pep_aston.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundExceptionHandler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<?> BankAccountNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BankAccountDoesNotBelongUserException.class)
    public ResponseEntity<?> BankAccountDoesNotBelongUserException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(BankAccountNotCorrectMoney.class)
    public ResponseEntity<?> BankAccountNotEnoughMoney() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(RequestBodyValidationException.class)
    public ResponseEntity<?> RequestBodyValidationException() {
        return ResponseEntity.badRequest().build();
    }
}
