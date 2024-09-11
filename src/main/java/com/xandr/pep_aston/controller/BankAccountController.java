package com.xandr.pep_aston.controller;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.TransferDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.exception.RequestBodyValidationException;
import com.xandr.pep_aston.service.BankAccountService;
import com.xandr.pep_aston.util.HashCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @PostMapping("/create")
    public ResponseEntity<BankAccountDto> createBankAccount(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new RequestBodyValidationException("Validation RequestBody Exception for UserDto");
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

    @SneakyThrows
    @PostMapping("/transfer")
    public ResponseEntity<BankAccountDto> transferMoney(@RequestBody @Validated TransferDto transferDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new RequestBodyValidationException("Validation RequestBody Exception for TransferDto");
        }

        transferDto.setPin(HashCodeUtil.getSHA256Hash(transferDto.getPin()));

        return bankAccountService.transferMoney(transferDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}