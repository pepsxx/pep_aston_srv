package com.xandr.pep_aston.controller;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.TransferDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.log.Logger;
import com.xandr.pep_aston.log.LoggerType;
import com.xandr.pep_aston.service.BankAccountService;
import com.xandr.pep_aston.util.HashCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/report")
    public ResponseEntity<List<BankAccountDto>> report() {

        return bankAccountService.report()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping("/transfer")
    public ResponseEntity<BankAccountDto> transferMoney(@RequestBody @Validated TransferDto transferDto, BindingResult bindingResult) {

        if (this.isNotValid(bindingResult)) {
            return ResponseEntity.badRequest().build();
        }

        transferDto.setPin(HashCodeUtil.getSHA256Hash(transferDto.getPin()));

        return bankAccountService.transferMoney(transferDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    private boolean isNotValid(BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("Validation errors: {}", bindingResult.getFieldErrors());
            return true;
        } else {
            log.info("Validation successful");
            return false;
        }

    }
}