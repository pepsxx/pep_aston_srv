package com.xandr.pep_aston.controller;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.UserDto;
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
            log.error("Validation errors: {}", bindingResult.getFieldErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String userName = userDto.getName();
        log.info("Begin create new BankAccount for {}.", userName);
        userDto.setPin(HashCodeUtil.getSHA256Hash(userDto.getPin()));

        return bankAccountService.createBankAccount(userDto)
                .map(ba -> {
                    log.info("The BankAccount for {} was created successfully. NumberBankAccount {}.", userName, ba.getNumberAccount());
                    return ResponseEntity.status(HttpStatus.CREATED).body(ba);
                })
                .orElseGet(() -> {
                    log.info("The BankAccount for {} do not created.", userName);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                });

    }
}