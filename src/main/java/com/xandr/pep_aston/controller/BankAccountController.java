package com.xandr.pep_aston.controller;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.log.Logger;
import com.xandr.pep_aston.log.LoggerType;
import com.xandr.pep_aston.service.BankAccountService;
import com.xandr.pep_aston.util.HashCodeUtil;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/bank_account")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity<BankAccountDto> createBankAccount(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // Не получилось убрать лог в aop, разобраться с Андреем
            Logger.setLogg(LoggerType.ERR, this.getClass().getSimpleName(), "Validation errors: %s".formatted(bindingResult.getFieldErrors()));
            return ResponseEntity.badRequest().build();
        }

        userDto.setPin(HashCodeUtil.getSHA256Hash(userDto.getPin()));

        return bankAccountService.createBankAccount(userDto)
                .map(ba -> ResponseEntity.status(HttpStatus.CREATED).body(ba))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

    }
}