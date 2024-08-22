package com.xandr.pep_aston.controller;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.service.BankAccountService;
import com.xandr.pep_aston.util.HashCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<BankAccountDto> createBankAccount(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("Validation errors : {}", bindingResult.getFieldErrors());
            return ResponseEntity.badRequest().body(null);
        }

        String userName = userDto.getName();
        log.info("Начало попытки создание нового счета для {}", userName);
        userDto.setPin(HashCodeUtil.getSHA256Hash(userDto.getPin()));

        Optional<BankAccountDto> maybeBankAccountDto = bankAccountService.createBankAccount(userDto);

        maybeBankAccountDto.ifPresentOrElse(
                ba -> logFinal(userName, "Создан счет № %d".formatted(ba.getNumberAccount())),
                () -> logFinal(userName, "Счет не создан"));

        return maybeBankAccountDto
                .map(r -> ResponseEntity.status(201).body(maybeBankAccountDto.get()))
                .orElse(ResponseEntity.status(404).body(null));
    }

    private void logFinal(String userName, String message) {
        String massagePrefix = "Результат попытки создание нового счета для %s: ".formatted(userName);
        log.info("{}{}", massagePrefix, message);
    }
}