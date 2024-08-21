package com.xandr.pep_aston.controller;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bank_account")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity<String> createBankAccount(@RequestBody UserDto userDto) {

        final String USER_NAME = userDto.getName();

        log.info("Начало попытки создание нового счета для {}", USER_NAME);
        Optional<BankAccountDto> maybeBankAccountDto = bankAccountService.createBankAccount(userDto);

        String message = "Результат попытки создание нового счета для";
        String finalMessage = maybeBankAccountDto
                .map(m -> message + " %s: Создан счет № %d".formatted(USER_NAME, maybeBankAccountDto.get().getNumberAccount()))
                .orElse(message + " %s: Счет не создан".formatted(USER_NAME));
        log.info(finalMessage);

        return maybeBankAccountDto
                .map(r -> new ResponseEntity<>(finalMessage, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(finalMessage, HttpStatus.NOT_ACCEPTABLE));
    }
}