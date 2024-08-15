package com.xandr.pep_aston.controller;

import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/bank_account")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @PostMapping("/creat")
    public boolean save(@RequestBody User user) {
        log.info("Начало попытки создание нового счета для {}", user.getName());
        boolean save = bankAccountService.save(user);
        log.info("Результат попытки создание нового счета для {}: {}", user.getName(), save);
        return save;
    }
}